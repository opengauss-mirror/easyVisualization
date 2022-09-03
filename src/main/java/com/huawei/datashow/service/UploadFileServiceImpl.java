package com.huawei.datashow.service;

import com.huawei.datashow.util.fileUtils.CSVUtil;
import com.huawei.datashow.util.fileUtils.ExcelUtil;
import com.huawei.datashow.util.fileUtils.YAMLUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


@Service
public class UploadFileServiceImpl implements UploadFileService
{
    @Override
    public void handleXlsOrXlsxFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException();
        } else {
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));
            if (fileType.equals(".xls")) {
                Workbook workbook = new HSSFWorkbook(file.getInputStream());
                ExcelUtil.parseExcel(workbook, ExcelUtil.XLS_FILE, file.getOriginalFilename().replace(fileType, ""));
            }
            else if (fileType.equals(".xlsx")) {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                ExcelUtil.parseExcel(workbook, ExcelUtil.XLSX_FILE, file.getOriginalFilename().replace(fileType, ""));
            }
            else {
                throw new IOException();
            }
        }
    }

    @Override
    public void handleCSVFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException();
        }
        else {
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));
            if (fileType.equals(".txt") || fileType.equals(".csv")) {
                CSVFormat format = CSVFormat.DEFAULT.withHeader();
                List<Map> list = new ArrayList<>();

                InputStreamReader isr = new InputStreamReader(file.getInputStream(),"UTF-8");
                CSVParser records = new CSVParser(isr,format);
                for (CSVRecord record : records) {
                    Map map = record.toMap();
                    list.add(map);
                }
                CSVUtil.writeCSVFile(file.getOriginalFilename().replace(fileType,""), list, false);
                YAMLUtil.createYAMLFile(file.getOriginalFilename().replace(fileType,""));
            }
            else {
                throw new IOException();
            }
        }
    }
}
