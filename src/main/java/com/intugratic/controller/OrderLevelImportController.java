package com.intugratic.controller;

import com.intugratic.service.OrderLevelImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/order-level")
public class OrderLevelImportController {

    private final OrderLevelImportService service;

    public OrderLevelImportController(OrderLevelImportService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadOrderLevelExcel(
            @RequestParam("file") MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Please upload a valid Excel file");
        }

        try {
            service.importExcel(file);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Order level settlement report imported successfully");
        } catch (Exception e) {
            e.printStackTrace();   // 🔥 PRINT FULL STACK TRACE
            throw e;               // 🔥 LET SPRING LOG IT
        }
    }

}