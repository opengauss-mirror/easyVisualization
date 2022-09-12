package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.util.fileUtils.CSVUtil;
import com.huawei.datashow.util.fileUtils.CommonUtil;
import com.huawei.datashow.util.fileUtils.YAMLUtil;
import com.huawei.shade.org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.datashow.util.fileUtils.CSVUtil.SHOW_DATA_SOURCE_MODE;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class UploadFileServiceImplTest {
    public static String DATA_SOURCE_DIR = System.getProperty("user.dir") + "/test-resources";

    @Autowired
    private UploadFileService uploadFileServiceImpl;

    @Test
    void handleXlsOrXlsxFile_xlsx() throws IOException {
        File file = new File(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        uploadFileServiceImpl.handleXlsOrXlsxFile(multipartFile);
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
    void handleXlsOrXlsxFile_xls() throws IOException{
        File file = new File(DATA_SOURCE_DIR + "/test_xls.xls");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        uploadFileServiceImpl.handleXlsOrXlsxFile(multipartFile);
        String software_list = CSVUtil.readCSVFile("test_xls", 1, 0, SHOW_DATA_SOURCE_MODE);
        Map<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        map.put("id", "0");
        map.put("success_percent", "0.6917817");
        map.put("failure_percent", "0.44186792");
        map.put("unknown_percent", "0.9158162");
        list1.add(map);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("sourceData", list1);
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("success_percent");
        list.add("failure_percent");
        list.add("unknown_percent");
        map1.put("columnNames", list);
        String toJSONString = JSON.toJSONString(map1);
        Assertions.assertEquals(toJSONString, software_list);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/test_xls");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test_xls-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }


    @Test
    void handleCSVFile() throws Exception {
        List<Map> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("age1", i);
            map.put("age2", i * 2);
            list.add(map);
        }
        YAMLUtil.createYAMLFile("testCSVa");
        CSVUtil.writeCSVFile("testCSVa.csv", list, true);
        File file = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        uploadFileServiceImpl.handleCSVFile(multipartFile);
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
        Assertions.assertEquals(toJSONString, "{\"sourceData\":[{\"age1\":0,\"age2\":0}],\"columnNames\":[\"age1\",\"age2\"]}");
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/testCSVa-edit.yaml");
        File csvDir2 = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa.csv");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
        if (csvDir2.exists()) {
            csvDir2.delete();
        }
    }
}