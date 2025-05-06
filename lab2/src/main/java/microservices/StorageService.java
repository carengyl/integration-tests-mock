package microservices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageService {

    private final List<String> calculationHistory = new ArrayList<>();

    @PostMapping("/save")
    public ResponseEntity<String> saveResult(
            @RequestParam double x,
            @RequestParam double result
    ) {
        String entry = "x=" + x + ", result=" + result;
        calculationHistory.add(entry);
        return ResponseEntity.ok("Saved: " + entry);
    }

    @GetMapping("/history")
    public ResponseEntity<List<String>> getHistory() {
        return ResponseEntity.ok(calculationHistory);
    }

    @GetMapping("/clear")
    public ResponseEntity<String> clearHistory() {
        calculationHistory.clear();
        return ResponseEntity.ok("History cleared");
    }
}