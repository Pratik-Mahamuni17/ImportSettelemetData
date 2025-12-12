package com.intugratic.service.impl;



import com.intugratic.entities.OrderLevelReport;
import com.intugratic.repository.OrderLevelReportRepository;
import com.intugratic.service.OrderImportService;
import com.intugratic.util.ExcelOrderParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderImportServiceImpl implements OrderImportService {

    private final OrderLevelReportRepository orderRepo;

    @Override
    @Transactional
    public void importOrderLevelReport(MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        try (InputStream inputStream = file.getInputStream()) {

            // Parse Excel → List<OrderLevelReport>
            List<OrderLevelReport> orders = ExcelOrderParser.parse(inputStream);

            if (orders == null || orders.isEmpty()) {
                throw new RuntimeException("No data found in file");
            }

            // Save all rows into DB
            orderRepo.saveAll(orders);
        }
    }
}

