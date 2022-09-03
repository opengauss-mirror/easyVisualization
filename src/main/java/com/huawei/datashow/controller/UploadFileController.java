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
            return Result.error("接口调用失败");
        }
    }

    @PostMapping("/uploadCSVFile")
    public Result<Object> uploadCSVFile(MultipartFile file)
    {
        try {
            UploadFileServiceImpl.handleCSVFile(file);
            return Result.OK();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return Result.error("接口调用失败");
        }
    }

    @PostMapping("/uploadTXTFile")
    public String uploadTXTFile(MultipartFile file, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')).equals(".txt"))
        {
            try
            {
                httpServletRequest.getRequestDispatcher("/uploadCSVFile").forward(httpServletRequest,httpServletResponse);
            }
            catch (ServletException servletException)
            {
                servletException.printStackTrace();
                return "fail";
            }
            catch (IOException ioException)
            {
                return "fail";
            }
            return "redirect";
        }
        else
        {
            return "unfit";
        }
    }
}
