package com.huawei.datashow.util.fileUtils;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.huawei.datashow.util.fileUtils.CSVUtil.SHOW_DATA_SOURCE_MODE;


class ExcelUtilTest {
    public static String DATA_SOURCE_DIR = System.getProperty("user.dir") + "/test-resources";

    @Test
    void getSheet() throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        Sheet sheet = ExcelUtil.getSheet(workbook, 0);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        XSSFSheet sheet1 = xssfWorkbook.getSheetAt(0);
        int rowCount = ExcelUtil.getRowCount(sheet);
        FormulaEvaluator formulaEvaluator = ExcelUtil.getFormulaEvaluator(workbook, ExcelUtil.XLSX_FILE);
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Row row1 = sheet1.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();
            Iterator<Cell> cellIterator1 = row1.cellIterator();
            while (cellIterator.hasNext() && cellIterator1.hasNext()) {
                Cell cell = cellIterator.next();
                Cell cell1 = cellIterator1.next();
                Assertions.assertEquals(ExcelUtil.getCellValue(cell1, formulaEvaluator), ExcelUtil.getCellValue(cell, formulaEvaluator));
            }
        }
    }

    @Test
    void getFormulaEvaluator() throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        FormulaEvaluator formulaEvaluator = ExcelUtil.getFormulaEvaluator(workbook, ExcelUtil.XLSX_FILE);
        Assertions.assertEquals(new XSSFFormulaEvaluator((XSSFWorkbook) workbook).getClass(), formulaEvaluator.getClass());
    }

    @Test
    void getRowCount() throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        Sheet sheet = ExcelUtil.getSheet(workbook, 0);
        Assertions.assertEquals(12, ExcelUtil.getRowCount(sheet));
    }

    @Test
    void getCellValue() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
        Row row = sheet.getRow(0);
        Iterator<Cell> cellIterator = row.cellIterator();
        Cell cell = cellIterator.next();
        Assertions.assertEquals(ExcelUtil.getCellValue(cell, formulaEvaluator), "Third");
    }

    @Test
    void getColumnNames() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<String> columnNames = ExcelUtil.getColumnNames(sheet);
        List<String> expected = new ArrayList<>();
        expected.add("Third");
        expected.add("Edition");
        expected.add("License");
        Assertions.assertEquals(expected, columnNames);
    }

    @Test
    void row2map() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<String> columnNames = ExcelUtil.getColumnNames(sheet);
        Row row = sheet.getRow(1);
        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
        Map<String, String> row2map = ExcelUtil.row2map(columnNames, row, formulaEvaluator);
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("Third", "spring-boot-starter-web");
        expected.put("Edition", "2.3.7.RELEASE");
        expected.put("License", "Apache 2.0");
        Assertions.assertEquals(expected, row2map);
    }

    @Test
    void parseExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx"));
        ExcelUtil.parseExcel(workbook, ExcelUtil.XLSX_FILE, "DataShowGUI_Open_Source_Software_List");
        String software_list = CSVUtil.readCSVFile("DataShowGUI_Open_Source_Software_List", 1, 0, SHOW_DATA_SOURCE_MODE);
        Map<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        map.put("Third", "spring-boot-starter-web");
        map.put("Edition", "2.3.7.RELEASE");
        map.put("License", "Apache 2.0");
        list1.add(map);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("sourceData", list1);
        List<String> list = new ArrayList<>();
        list.add("Third");
        list.add("Edition");
        list.add("License");
        map1.put("columnNames", list);
        String toJSONString = JSON.toJSONString(map1);
        Assertions.assertEquals(toJSONString, software_list);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/DataShowGUI_Open_Source_Software_List-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void parseExcel_batch() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(DATA_SOURCE_DIR + "/test_batch.xlsx"));
        ExcelUtil.parseExcel(workbook, ExcelUtil.XLSX_FILE, "test_batch");
        String batch = CSVUtil.readCSVFile("test_batch", 1, 0, SHOW_DATA_SOURCE_MODE);
        Map<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        map.put("random_number", "1");
        list1.add(map);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("sourceData", list1);
        List<String> list = new ArrayList<>();
        list.add("random_number");
        map1.put("columnNames", list);
        String toJSONString = JSON.toJSONString(map1);
        Assertions.assertEquals(toJSONString, batch);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/test_batch");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test_batch-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }
}