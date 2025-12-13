package com.intugratic.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intugratic.entities.OrderLevelReport;
import com.intugratic.repository.OrderLevelReportRepository;
import com.intugratic.service.OrderLevelImportService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OrderLevelImportServiceImpl implements OrderLevelImportService {

    private final OrderLevelReportRepository repository;
    private final DataFormatter formatter = new DataFormatter();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderLevelImportServiceImpl(OrderLevelReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public void importExcel(MultipartFile file) throws Exception {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        // find order-level sheet
        Sheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet temp = workbook.getSheetAt(i);
            if (temp.getSheetName().toLowerCase().contains("order")) {
                sheet = temp;
                break;
            }
        }

        if (sheet == null) {
            throw new RuntimeException("Order level sheet not found");
        }

        int HEADER_ROW = 6;   // header row
        int START_ROW  = 8;   // first order row

        Row headerRow = sheet.getRow(HEADER_ROW);

        for (int i = START_ROW; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) continue;

            String orderId = getString(row.getCell(1));
            LocalDate orderDate = getDate(row.getCell(2));

            if (orderId == null || orderId.isEmpty() || orderDate == null) {
                continue;
            }

            if (repository.existsByOrderId(orderId)) {
                continue;
            }

            // 🔑 Build JSON dynamically for ALL columns
            Map<String, String> rowData = new LinkedHashMap<>();

            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                String header = formatter.formatCellValue(headerRow.getCell(c));
                String value  = formatter.formatCellValue(row.getCell(c));
                rowData.put(header, value);
            }

            String json = objectMapper.writeValueAsString(rowData);

            OrderLevelReport report = new OrderLevelReport();
            report.setOrderId(orderId);
            report.setOrderDate(orderDate);
            report.setOrderLevelJson(json);

            repository.save(report);
        }

        workbook.close();
    }

    // ---------- helpers ----------

    private String getString(Cell cell) {
        return cell == null ? null : formatter.formatCellValue(cell).trim();
    }

    private LocalDate getDate(Cell cell) {
        try {
            if (cell == null) return null;

            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            }

            String value = formatter.formatCellValue(cell).trim();
            return value.isEmpty() ? null : LocalDate.parse(value.substring(0, 10));

        } catch (Exception e) {
            return null;
        }
    }
}
