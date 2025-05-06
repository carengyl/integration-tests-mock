package microservices;

import functions.FunctionSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api/calculate")
public class CalculatorService {

    @Autowired
    private RestTemplate restTemplate;
    private final FunctionSystem system = new FunctionSystem();

    private final String STORAGE_SERVICE_URL = "http://localhost:8081/api/storage";

    @PostMapping
    public ResponseEntity<?> calculate(@RequestParam double x) {
        try {
            double result = system.calculate(x);

            String saveUrl = STORAGE_SERVICE_URL + "/save?x=" + x + "&result=" + result;
            restTemplate.postForEntity(saveUrl, null, String.class);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Calculation failed: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        try {
            String historyUrl = STORAGE_SERVICE_URL + "/history";
            return restTemplate.getForEntity(historyUrl, String.class);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to fetch history: " + e.getMessage());
        }
    }
}