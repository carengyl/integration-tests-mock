package microservices;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalculatorServiceIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081))
            .build();

    @Test
    void calculate_ShouldReturnResultAndSaveToStorage() {
        double input = 3.0;
        double expectedResult = 10.0; // 3^2 + 1

        wireMockServer.stubFor(post(urlPathEqualTo("/api/storage/save"))
                .withQueryParam("x", equalTo(String.valueOf(input)))
                .withQueryParam("result", equalTo(String.valueOf(expectedResult)))
                .willReturn(aResponse().withStatus(200)));

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/calculate?x=" + input, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.valueOf(expectedResult), response.getBody());

        wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/storage/save"))
                .withQueryParam("x", equalTo(String.valueOf(input)))
                .withQueryParam("result", equalTo(String.valueOf(expectedResult))));
    }

    @Test
    void calculate_ShouldReturnErrorWhenStorageUnavailable() {
        double input = 2.0;

        wireMockServer.stubFor(post(urlPathEqualTo("/api/storage/save"))
                .willReturn(aResponse().withStatus(503)
                        .withFixedDelay(2000))); // Simulate timeout

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/calculate?x=" + input, null, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Failed to save result"));
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