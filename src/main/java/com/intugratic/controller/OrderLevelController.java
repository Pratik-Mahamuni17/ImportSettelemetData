package com.intugratic.controller;

import com.intugratic.service.OrderLevelImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/order-level")
public class OrderLevelController {

    private final OrderLevelImportService service;

    public OrderLevelController(OrderLevelImportService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("file") MultipartFile file) throws Exception {

        service.importExcel(file);
        return ResponseEntity.ok("Order-level settlement report imported successfully");
    }
}
