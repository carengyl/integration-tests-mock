package microservices;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class CalculatorServiceIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int applicationPort;

    @Value("${wiremock.server.port}")
    private int wireMockPort;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081))
            .build();

    @BeforeEach
    void setup() {
        WireMock.reset();
        System.setProperty("storage.service.url",
                "http://localhost:" + wireMockPort + "/api/storage");
    }

    @Test
    void calculate_ShouldReturnResultAndSaveToStorage() {
        WireMock.configureFor("localhost", 8081);

        stubFor(post(urlPathEqualTo("/api/storage/save"))
                .withQueryParam("x", equalTo("3.0"))
                .withQueryParam("result", equalTo("0.35241954572197937"))
                .willReturn(aResponse().withStatus(200)));

        String url = String.format("http://localhost:%d/api/calculate?x=3.0", applicationPort);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        verify(postRequestedFor(urlPathEqualTo("/api/storage/save"))
                .withQueryParam("x", equalTo("3.0"))
                .withQueryParam("result", equalTo("0.35241954572197937")));
    }

    @Test
    void calculate_ShouldReturnErrorWhenStorageUnavailable() {
        double input = 2.0;

        stubFor(post(urlPathEqualTo("/api/storage/save"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withFixedDelay(2000)));

        String calculateUrl = "http://localhost:" + applicationPort + "/api/calculate?x=" + input;
        ResponseEntity<String> response = restTemplate.postForEntity(calculateUrl, null, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Calculation failed"));
    }

    @Test
    void getHistory_ShouldReturnDataFromStorage() {
        String mockHistory = "[\"x=1.0, result=2.0\", \"x=2.0, result=5.0\"]";

        wireMockServer.stubFor(get(urlPathEqualTo("/api/storage/history"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockHistory)));

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/calculate/history", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockHistory, response.getBody());
    }

    @Test
    void getHistory_ShouldHandleStorageError() {
        wireMockServer.stubFor(get(urlPathEqualTo("/api/storage/history"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Storage error")));

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/calculate/history", String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Failed to fetch history"));
    }
}