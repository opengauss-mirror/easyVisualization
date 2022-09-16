package com.huawei.datashow.util.fileUtils;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class CSVUtilTest {

    @Test
    void getPath() {
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/test");
        if (!csvDir.exists()) {
            try {
                csvDir.createNewFile();
                Assertions.assertEquals(csvDir.getPath(), CSVUtil.getPath("test"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                csvDir.delete();
            }
        }
    }

    @Test
    void writeCSVFile() throws IOException {
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("age1", i);
            map.put("age2", i * 2);
            list.add(map);
        }
        YAMLUtil.createYAMLFile("testCSVa.csv");
        Map<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        map.put("age1", 0);
        map.put("age2", 0);
        list1.add(map);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("sourceData", list1);
        List<String> list2 = new ArrayList<>();
        list2.add("age1");
        list2.add("age2");
        map1.put("columnNames", list2);
        String toJSONString = JSON.toJSONString(map1);
        try {
            CSVUtil.writeCSVFile("testCSVa.csv", list, true);
            Assertions.assertEquals(toJSONString, CSVUtil.readCSVFile("testCSVa.csv", 1, 0, CSVUtil.SHOW_DATA_SOURCE_MODE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa.csv");
            File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/testCSVa.csv-edit.yaml");
            if (csvDir.exists()) {
                csvDir.delete();
            }
            if (yamlDir.exists()) {
                yamlDir.delete();
            }
        }
    }

    @Test
    void readCSVFile() throws IOException {
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("age1", i);
            map.put("age2", i * 2);
            list.add(map);
        }
        YAMLUtil.createYAMLFile("testCSVa.csv");
        Map<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        map.put("age1", 0);
        map.put("age2", 0);
        list1.add(map);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("sourceData", list1);
        List<String> list2 = new ArrayList<>();
        list2.add("age1");
        list2.add("age2");
        map1.put("columnNames", list2);
        String toJSONString = JSON.toJSONString(map1);
        CSVUtil.writeCSVFile("testCSVa.csv", list, true);
        try {
            String readCSVFile = CSVUtil.readCSVFile("testCSVa.csv", 1, 0, CSVUtil.SHOW_DATA_SOURCE_MODE);
            Assertions.assertEquals(toJSONString, readCSVFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa.csv");
            File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/testCSVa.csv-edit.yaml");
            if (csvDir.exists()) {
                csvDir.delete();
            }
            if (yamlDir.exists()) {
                yamlDir.delete();
            }
        }

    }

    @Test
    void getRowCount() throws IOException {
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("age1", i);
            map.put("age2", i * 2);
            list.add(map);
        }
        CSVUtil.writeCSVFile("test", list, true);
        Assertions.assertEquals(10, CSVUtil.getRowCount("test"));
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/test");
        if (csvDir.exists()) {
            csvDir.delete();
        }
    }
    @Test
    void removeCSVFile() {
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/test");
        if (!csvDir.exists()) {
            try {
                csvDir.createNewFile();
                CSVUtil.removeCSVFile("test");
                Assertions.assertTrue(!csvDir.exists());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}