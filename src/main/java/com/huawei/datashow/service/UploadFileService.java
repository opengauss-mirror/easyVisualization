package com.huawei.datashow.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface UploadFileService
{
    void handleXlsOrXlsxFile(MultipartFile file) throws IOException;
    void handleCSVFile(MultipartFile file) throws IOException;
}
