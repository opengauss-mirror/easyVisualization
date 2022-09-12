package com.huawei.datashow.controller;


import com.huawei.datashow.service.UploadFileService;
import com.huawei.datashow.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadFileController
{
    @Autowired
    UploadFileService UploadFileServiceImpl;

    @PostMapping("/uploadXlsOrXlsxFile")
    public Result<Object> uploadXlsOrXlsxFile(MultipartFile file)
    {
        try {
            UploadFileServiceImpl.handleXlsOrXlsxFile(file);
            return Result.OK();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return Result.error("Uploaded! But File Is Empty Or Not XLS/XLSX File!");
        }
    }

    @PostMapping("/uploadCSVOrTxtFile")
    public Result<Object> uploadCSVFile(MultipartFile file)
    {
        try {
            UploadFileServiceImpl.handleCSVFile(file);
            return Result.OK();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return Result.error("Uploaded! But File Is Empty Or Not CSV/TXT File!");
        }
    }
}
