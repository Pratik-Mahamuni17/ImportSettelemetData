package com.intugratic.service;

import org.springframework.web.multipart.MultipartFile;

public interface OrderLevelImportService {

    void importExcel(MultipartFile file) throws Exception;
}
