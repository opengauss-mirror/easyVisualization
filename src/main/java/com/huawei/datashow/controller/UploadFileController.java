package com.huawei.datashow.controller;


import com.huawei.datashow.service.UploadFileService;
import com.huawei.datashow.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            return Result.error("已上传，但文件为空或格式不匹配，请删除并重新上传！");
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
            return Result.error("已上传，但上传文件为空或格式不匹配，请删除并重新上传！");
        }
    }
}
