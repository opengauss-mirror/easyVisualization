package com.huawei.datashow.util.fileUtils;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.bean.DataSourceEditBean;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collections;


public class CSVUtil {
    public static int SHOW_DATA_SOURCE_MODE = 0;
    public static int SAVE_DATA_SOURCE_EDIT_MODE = 1;

    /**
     * Get path of CSV file
     * @param fileName
     * @return
     */
    public static String getPath(String fileName) {
        return CommonUtil.DATA_SOURCE_DIR + "/" + fileName;
    }

    /**
     * Write to CSV file
     * @param fileName
     * @param sourceData
     */
    public static void writeCSVFile(String fileName, List<Map> sourceData, boolean appendFlag) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        String csvFilePath = getPath(fileName);
        try{
            fileWriter = new FileWriter(csvFilePath, appendFlag);

            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withIgnoreHeaderCase());

            for (Map map : sourceData) {
                String jsonMap = JSON.toJSONString(map);
                csvFilePrinter.printRecord(jsonMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Read CSV file
     * @param fileName
     * @param limit
     * @param startIndex
     * @param mode SHOW_DATA_SOURCE_MODE：Read to the front-end display (the data marked for deletion is left blank)
     *             SAVE_DATA_SOURCE_EDIT_MODE：Read for saving modification (data marked for deletion will not be read)
     */
    public static String readCSVFile(String fileName, int limit, int startIndex, int mode) throws IOException {
        int rowCount = getRowCount(fileName);

        Reader in = new FileReader(getPath(fileName));

        DataSourceEditBean dataSourceEditBean = YAMLUtil.readYAMLFile(fileName);

        List<Integer> deleteRowIndex = dataSourceEditBean.getDeleteRowIndex();
        Collections.sort(deleteRowIndex);

        List<String> deleteColumnName = dataSourceEditBean.getDeleteColumnName();

        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        CSVParser parser = csvFileFormat.parse(in);
        List<CSVRecord> csvRecords = parser.getRecords();

        List<Map> list = new ArrayList<>();
        for (int i = startIndex; i < limit + startIndex; i++) {
            if (i == rowCount) {
                break;
            }

            Map map = JSON.parseObject(csvRecords.get(i).get(0), LinkedHashMap.class);

            for (String columnName : deleteColumnName) {
                map.remove(columnName);
            }

            if (mode == 0) {
                if (deleteRowIndex.contains(i)) {
                    map.clear();
                }
                list.add(map);

            } else if (mode == 1) {
                if (!deleteRowIndex.contains(i)) {
                    list.add(map);
                }
            }

        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("sourceData", list);
        LinkedHashMap linkedHashMap = JSON.parseObject(csvRecords.get(0).get(0), LinkedHashMap.class);
        for (String columnName : deleteColumnName) {
            linkedHashMap.remove(columnName);
        }
        result.put("columnNames", linkedHashMap.keySet());
        in.close();
        return JSON.toJSONString(result);
    }

    /**
     * Get CSV file size
     * @param fileName
     * @return
     * @throws IOException
     */
    public static int getRowCount(String fileName) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(getPath(fileName)));
        int rowCount=0;
        while(br.readLine()!=null){
            rowCount++;
        }
        br.close();
        return rowCount;
    }

    /**
     * Remove CSV file
     * @param fileName
     */
    public static void removeCSVFile(String fileName) throws IOException {
        Files.delete(Paths.get(getPath(fileName)));
    }



}
