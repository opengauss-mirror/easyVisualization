package com.huawei.datashow.util.fileUtils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Date;
import java.util.Iterator;



public class ExcelUtil {
    public static int XLSX_FILE = 0;
    public static int XLS_FILE = 1;
    /**
     * Get sheet
     * @param workbook
     * @param sheetIndex
     * @return
     */
    public static Sheet getSheet(Workbook workbook, int sheetIndex) {
        return workbook.getSheetAt(sheetIndex);
    }

    /**
     * Get formula evaluator
     * @param workbook
     * @param fileType File's type：XLSX_FILE XLS_FILE
     * @return
     */
    public static FormulaEvaluator getFormulaEvaluator(Workbook workbook, int fileType) {
        if (fileType == 0) {
            return new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        } else {
            return new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        }
    }

    /**
     * Get the total number of rows in the sheet
     * @param sheet sheet
     * @return
     */
    public static int getRowCount(Sheet sheet) {
        return sheet.getLastRowNum();
    }

    /**
     * Get real value of a cell
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }

        CellType cellType = cell.getCellType();
        String cellValue = "";

        switch (cellType) {
            case _NONE:
            case BLANK:
                cellValue = "UNKNOWN VALUE";
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = sdf.format(date);
                    cellValue = format;
                } else {
                    cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case FORMULA:
                cellValue = formulaEvaluator.evaluate(cell).formatAsString();
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case ERROR:
                cellValue = "ERROR VALUE";
                break;
        }
        return cellValue;
    }

    /**
     * Get column's name
     * @param sheet
     * @return
     */
    public static List<String> getColumnNames(Sheet sheet) {
        List<String> columnNames = new ArrayList<>();
        Row row = sheet.getRow(0);
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columnNames.add(cell.getStringCellValue());
        }
        return columnNames;
    }

    /**
     * Trans a row to a map
     * @param columnNames
     * @param row
     * @param formulaEvaluator
     * @return
     */
    public static Map<String, String> row2map(List<String> columnNames, Row row, FormulaEvaluator formulaEvaluator) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < columnNames.size(); i++) {
            Cell cell = row.getCell(i);
            String cellValue = getCellValue(cell, formulaEvaluator);
            map.put(columnNames.get(i), cellValue);
        }
        return map;
    }


    /**
     * Parse a excel file and save as a data source
     * @param workbook
     * @param fileType
     * @param fileName
     */
    public static void parseExcel(Workbook workbook, int fileType, String fileName) throws IOException {
        Sheet sheet = getSheet(workbook, 0);
        FormulaEvaluator formulaEvaluator = getFormulaEvaluator(workbook, fileType);
        List<String> columnNames = getColumnNames(sheet);
        int rowCount = getRowCount(sheet);
        int batch = rowCount / 1000;
        if (batch > 0) {

            for (int i = 0; i < batch; i++) {
                List<Map> list = new ArrayList<>();

                for (int j = 1; j <= 1000; j++) {
                    Row row = sheet.getRow(i * 1000 + j);
                    Map<String, String> stringStringMap = row2map(columnNames, row, formulaEvaluator);
                    list.add(stringStringMap);
                }
                CSVUtil.writeCSVFile(fileName, list, i != 0);
            }
            List<Map> list = new ArrayList<>();
            for (int i = batch * 1000 + 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                Map<String, String> stringStringMap = row2map(columnNames, row, formulaEvaluator);
                list.add(stringStringMap);
            }
            CSVUtil.writeCSVFile(fileName, list, true);
        } else {
            List<Map> list = new ArrayList<>();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                Map<String, String> stringStringMap = row2map(columnNames, row, formulaEvaluator);
                list.add(stringStringMap);
            }
            CSVUtil.writeCSVFile(fileName, list, true);
        }
        YAMLUtil.createYAMLFile(fileName);
    }
}
