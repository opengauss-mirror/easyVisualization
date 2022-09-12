package com.huawei.datashow.controller;

import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.util.Result;
import com.huawei.datashow.util.fileUtils.CommonUtil;
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


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class UploadFileControllerTest {
    public static String DATA_SOURCE_DIR = System.getProperty("user.dir") + "/test-resources";

    @Autowired
    private UploadFileController uploadFileController;

    @Test
    void uploadXlsOrXlsxFile() throws IOException {
        File file = new File(DATA_SOURCE_DIR + "/DataShowGUI_Open_Source_Software_List.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Result<Object> result = uploadFileController.uploadXlsOrXlsxFile(multipartFile);
        Assertions.assertEquals(result.OK(), result);
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
    void uploadXlsOrXlsxFile_when_fail() throws IOException {
        File file = new File(DATA_SOURCE_DIR + "/test.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Result<Object> result = uploadFileController.uploadXlsOrXlsxFile(multipartFile);
        Assertions.assertEquals("Empty Or Not XLS/XLSX File!", result.getMessage());
        file.delete();
    }

    @Test
    void uploadCSVFile() throws IOException {
        File file = new File(DATA_SOURCE_DIR + "/testCSVa.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Result<Object> result = uploadFileController.uploadCSVFile(multipartFile);
        Assertions.assertEquals(Result.OK(), result);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/testCSVa-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void uploadCSVFile_when_fail() throws IOException {
        File file = new File(DATA_SOURCE_DIR + "/test_null.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Result<Object> result = uploadFileController.uploadCSVFile(multipartFile);
        Assertions.assertEquals("Empty Or Not CSV/TXT File!", result.getMessage());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/testCSVa");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/testCSVa-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }
}