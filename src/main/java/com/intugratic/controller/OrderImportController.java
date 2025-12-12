package com.intugratic.controller;




import com.intugratic.service.OrderImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class OrderImportController {

    private final OrderImportService importService;

    @PostMapping("/order-level")
    public ResponseEntity<?> importOrderLevel(@RequestParam("file") MultipartFile file) {
        try {
            importService.importOrderLevelReport(file);
            return ResponseEntity.ok("Order Level Report imported successfully!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Import failed: " + e.getMessage());
        }
    }
}
