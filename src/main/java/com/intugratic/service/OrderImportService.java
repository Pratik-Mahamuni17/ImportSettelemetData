package com.intugratic.service;



import org.springframework.web.multipart.MultipartFile;

public interface OrderImportService {

    // Import Order Level Report Excel file
    void importOrderLevelReport(MultipartFile file) throws Exception;
}

