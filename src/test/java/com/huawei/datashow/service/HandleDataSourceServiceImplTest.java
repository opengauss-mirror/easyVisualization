package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.bean.DataSourceEditBean;
import com.huawei.datashow.util.MyException;
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
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class HandleDataSourceServiceImplTest {
    @Autowired
    ConnectionPoolService connectionPoolServiceImpl;

    @Autowired
    HandleDataSourceService handleDataSourceServiceImpl;


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
    void saveDataSource() throws MyException, IOException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        try {
            handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
            Assertions.assertTrue(csvDir.exists() && yamlDir.exists());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            csvDir.delete();
            yamlDir.delete();
        }
    }


    @Test
    void readDataSource() throws IOException, MyException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        try {
            handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
            Map<String, Object> map = new LinkedHashMap<>();
            List<Map<String, Object>> list1 = new ArrayList<>();
            map.put("success_percent", 0.6917817);
            map.put("failure_percent", 0.44186792);
            map.put("unknown_percent", 0.9158162);
            map.put("id", 0);
            list1.add(map);
            Map<String, Object> map1 = new LinkedHashMap<>();
            map1.put("sourceData", list1);
            List<String> list = new ArrayList<>();
            list.add("success_percent");
            list.add("failure_percent");
            list.add("unknown_percent");
            list.add("id");
            map1.put("columnNames", list);
            String toJSONString = JSON.toJSONString(map1);
            Assertions.assertEquals(toJSONString, handleDataSourceServiceImpl.readDataSource(dataSourceName, 0, 1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
            File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
            csvDir.delete();
            yamlDir.delete();
        }
    }

    @Test
    void getDataSourceSize() throws IOException, MyException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        try {
            handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
            Assertions.assertEquals(5000, handleDataSourceServiceImpl.getDataSourceSize(dataSourceName));
        } catch (Exception e) {
            throw new IOException();
        } finally {
            File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
            File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
            csvDir.delete();
            yamlDir.delete();
        }
    }

    @Test
    void removeDataSource() throws Exception {
        String dataSourceName = "test";
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        if (!csvDir.exists()) {
            csvDir.createNewFile();
        }
        if (!yamlDir.exists()) {
            yamlDir.createNewFile();
        }
        HandleDataSourceService handleDataSourceServiceImpl = new HandleDataSourceServiceImpl();
        handleDataSourceServiceImpl.removeDataSource(dataSourceName);
        Assertions.assertTrue(!csvDir.exists() || !yamlDir.exists());
    }

    @Test
    void getDataSourceList() throws IOException {
        String[] object = {"test"};
        String dataSourceName = "test";
        File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
        if (!csvDir.exists()) {
            csvDir.createNewFile();
        }
        String string = JSON.toJSONString(object);
        Assertions.assertEquals(string, handleDataSourceServiceImpl.getDataSourceList());
        csvDir.delete();
    }

    @Test
    void editDataSource() throws IOException {
        YAMLUtil.createYAMLFile("test");
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        List<Integer> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add(1);
        list.add(2);
        dataSourceEditBean.setDeleteRowIndex(list);
        dataSourceEditBean.setDeleteColumnName(list1);
        handleDataSourceServiceImpl.editDataSource("test", dataSourceEditBean);
        DataSourceEditBean sourceEditBean = YAMLUtil.readYAMLFile("test");
        Assertions.assertEquals(dataSourceEditBean, sourceEditBean);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        yamlDir.delete();
    }

    @Test
    void reloadDataSource() throws IOException {
        handleDataSourceServiceImpl.reloadDataSource(dataSourceName);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
        Assertions.assertTrue(yamlDir.exists());
        yamlDir.delete();
    }

    @Test
    void saveEditDataSource() throws IOException, MyException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        YAMLUtil.createYAMLFile("test");
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        List<String> list1 = new ArrayList<>();
        list1.add("success_percent");
        list1.add("failure_percent");
        dataSourceEditBean.setDeleteRowIndex(list);
        dataSourceEditBean.setDeleteColumnName(list1);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list2 = new ArrayList<>();
        map.put("unknown_percent", 0.075299);
        map.put("id", 2);
        list2.add(map);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("sourceData", list2);
        List<String> list3 = new ArrayList<>();
        list3.add("unknown_percent");
        list3.add("id");
        map1.put("columnNames", list3);
        String toJSONString = JSON.toJSONString(map1);
        try {
            handleDataSourceServiceImpl.saveDataSource(poolDTOBean.getPollName(), sql, dataSourceName);
            YAMLUtil.writeYAMLFile("test", dataSourceEditBean);
            handleDataSourceServiceImpl.saveEditDataSource(dataSourceName);
            Assertions.assertEquals(toJSONString, handleDataSourceServiceImpl.readDataSource(dataSourceName, 0, 1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            File csvDir = new File(CommonUtil.DATA_SOURCE_DIR + "/" + dataSourceName);
            File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + dataSourceName + "-edit.yaml");
            csvDir.delete();
            yamlDir.delete();
        }
    }

    @Test
    void getEditStatus_true() throws IOException {
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
        boolean flag = handleDataSourceServiceImpl.getEditStatus("test");
        Assertions.assertTrue(flag);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }

    @Test
    void getEditStatus_false() throws IOException {
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        YAMLUtil.createYAMLFile("test");
        YAMLUtil.writeYAMLFile("test", dataSourceEditBean);
        boolean flag = handleDataSourceServiceImpl.getEditStatus("test");
        Assertions.assertTrue(!flag);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        if (yamlDir.exists()) {
            yamlDir.delete();
        }
    }
}