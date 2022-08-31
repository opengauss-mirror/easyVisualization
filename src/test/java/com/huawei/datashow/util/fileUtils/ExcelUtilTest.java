package com.huawei.datashow.util.fileUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilTest {

    @Test
    void getSheet() throws IOException {
        Workbook workbook = new HSSFWorkbook(new FileInputStream("李亮杰_学生信息.xls"));
        Sheet sheet = ExcelUtil.getSheet(workbook, 0);
        System.out.println(ExcelUtil.getRowCount(sheet));
    }

    @Test
    void getFormulaEvaluator() throws IOException {
        Workbook workbook = new HSSFWorkbook(new FileInputStream("李亮杰_学生信息.xls"));
        Sheet sheet = ExcelUtil.getSheet(workbook, 0);
        FormulaEvaluator formulaEvaluator = ExcelUtil.getFormulaEvaluator(workbook, ExcelUtil.XLS_FILE);
        int rowCount = ExcelUtil.getRowCount(sheet);
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = ExcelUtil.getCellValue(cell, formulaEvaluator);
                System.out.println(cellValue);
            }
        }
    }

    @Test
    void getRowCount() {

    }

    @Test
    void getCellValue() {
    }

    @Test
    void getColumnNames() throws IOException {
        Workbook workbook = new HSSFWorkbook(new FileInputStream("李亮杰_学生信息.xls"));
        Sheet sheet = ExcelUtil.getSheet(workbook, 0);
        List<String> columnNames = ExcelUtil.getColumnNames(sheet);
        System.out.println(columnNames);
    }

    @Test
    void parseExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream("李亮杰_学生信息.xlsx"));
        ExcelUtil.parseExcel(workbook, ExcelUtil.XLSX_FILE, "学生信息2");
    }
}