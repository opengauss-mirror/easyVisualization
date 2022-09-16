package com.huawei.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.bean.DataSourceEditBean;
import com.huawei.datashow.bean.ParamDataSourceEditBean;
import com.huawei.datashow.service.ConnectionPoolService;
import com.huawei.datashow.service.HandleDataSourceService;
import com.huawei.datashow.util.MyException;
import com.huawei.datashow.util.Result;
import com.huawei.datashow.util.fileUtils.CommonUtil;
import com.huawei.datashow.util.fileUtils.YAMLUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class HandleDataSourceControllerTest {

    @Autowired
    private HandleDataSourceController handleDataSourceController;

    @Autowired
    private HandleDataSourceService handleDataSourceServiceImpl;

    @Autowired
    private ConnectionPoolService connectionPoolServiceImpl;

    String sql = "SELECT id,success_percent,failure_percent,unknown_percent FROM student WHERE id < '5000'";
    String dataSourceName = "test";

    ConnectionPoolDTOBean getPoolDTOBean() throws FileNotFoundException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        String path = System.getProperty("user.dir") + "/data-source/dto.yaml";
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        return yaml.loadAs(fileInputStream, ConnectionPoolDTOBean.class);
    }

    @Test
    void saveDataSource() throws MyException, FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        String message = handleDataSourceController.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName).getMessage();
        Assertions.assertEquals("success", message);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        csvDir.delete();
        yamlDir.delete();
    }

    @Test
    void saveDataSource_when_fail() throws MyException, FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        Result<Object> result = handleDataSourceController.saveDataSource("wrongPollName", sql, dataSourceName);
        String message = result.getMessage();
        assertEquals("error", message);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        csvDir.delete();
        yamlDir.delete();
    }


    @Test
    void readDataSource() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Integer code = handleDataSourceController.readDataSource(dataSourceName, 0, 1).getCode();
        Assertions.assertEquals(200, code);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        csvDir.delete();
        yamlDir.delete();
    }

    @Test
    void readDataSource_when_fail() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        String message = handleDataSourceController.readDataSource("wrongDataSourceName", 5000, 0).getMessage();
        assertEquals("error", message);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void getDataSourceSize() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Object result = handleDataSourceController.getDataSourceSize(dataSourceName).getResult();
        Assertions.assertEquals(5000, result);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void getDataSourceSize_when_fail() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        String message = handleDataSourceController.getDataSourceSize("wrongDataSourceName").getMessage();
        assertEquals("error", message);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void removeDataSource() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Result<Object> result = handleDataSourceController.removeDataSource(dataSourceName);
        Assertions.assertEquals("success", result.getMessage());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void removeDataSource_when_fail() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Result<Object> result = handleDataSourceController.removeDataSource("WrongDataSourceName");
        Assertions.assertEquals("error", result.getMessage());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void getDataSourceList() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Result<Object> dataSourceList = handleDataSourceController.getDataSourceList();
        List<String> list = new ArrayList<>();
        list.add(dataSourceName);
        String toJSONString = JSON.toJSONString(list);
        Assertions.assertEquals(toJSONString, dataSourceList.getResult());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }


    @Test
    void editDataSource() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        ParamDataSourceEditBean bean = new ParamDataSourceEditBean();
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        List<String> list1 = new ArrayList<>();
        list1.add("success_percent");
        dataSourceEditBean.setDeleteColumnName(list1);
        dataSourceEditBean.setDeleteRowIndex(list);
        bean.setDataSourceName(dataSourceName);
        bean.setDataSourceEditBean(dataSourceEditBean);
        Result<Object> result = handleDataSourceController.editDataSource(bean);
        Assertions.assertEquals(Result.OK("success"), result);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void editDataSource_when_fail() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
        ParamDataSourceEditBean bean = new ParamDataSourceEditBean();
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        List<String> list1 = new ArrayList<>();
        list1.add("success_percent");
        dataSourceEditBean.setDeleteColumnName(list1);
        dataSourceEditBean.setDeleteRowIndex(list);
        bean.setDataSourceName(dataSourceName);
        bean.setDataSourceEditBean(dataSourceEditBean);
        Result<Object> result = handleDataSourceController.editDataSource(bean);
        Assertions.assertEquals("error", result.getMessage());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        if (csvDir.exists()) {
            csvDir.delete();
        }
    }

    @Test
    void reloadDataSource() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Result<Object> result = handleDataSourceController.reloadDataSource(dataSourceName);
        Assertions.assertEquals("success", result.getMessage());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void saveEditDataSource() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Assertions.assertEquals(Result.OK().getCode(), handleDataSourceController.saveEditDataSource(dataSourceName).getCode());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void saveEditDataSource_when_fail() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
        Assertions.assertEquals("error", handleDataSourceController.saveEditDataSource("wrongDataSourceName").getMessage());
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (csvDir.exists()) {
            csvDir.delete();
        }
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }


    @Test
    void getEditStatus_success() throws IOException {
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        List<Integer> rowList = new ArrayList<>();
        rowList.add(1);
        rowList.add(2);
        dataSourceEditBean.setDeleteRowIndex(rowList);
        List<String> colName = new ArrayList<>();
        colName.add("test");
        dataSourceEditBean.setDeleteColumnName(colName);
        YAMLUtil.createYAMLFile("test");
        YAMLUtil.writeYAMLFile("test", dataSourceEditBean);
        Result<Object> result = handleDataSourceController.getEditStatus("test");
        Assertions.assertEquals(Result.OK(true), result);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }


    @Test
    void getEditStatus_fail() throws IOException {
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        YAMLUtil.createYAMLFile("test");
        YAMLUtil.writeYAMLFile("test", dataSourceEditBean);
        Result<Object> result = handleDataSourceController.getEditStatus("test");
        Assertions.assertEquals(false, result.getResult());
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }
}