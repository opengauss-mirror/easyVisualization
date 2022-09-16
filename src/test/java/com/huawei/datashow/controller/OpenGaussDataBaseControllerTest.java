package com.huawei.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.service.ConnectionPoolService;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class OpenGaussDataBaseControllerTest {

    @Autowired
    private OpenGaussDataBaseController openGaussDataBaseController;

    @Autowired
    private ConnectionPoolService connectionPoolServiceImpl;



    ConnectionPoolDTOBean getPoolDTOBean() throws FileNotFoundException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        String path = System.getProperty("user.dir") + "/data-source/dto.yaml";
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        return yaml.loadAs(fileInputStream, ConnectionPoolDTOBean.class);
    }

    String dataSourceName = "test";
    @Test
    void getSchema() throws Exception{
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        String sql = "SELECT schemaname FROM pg_tables group by schemaname";
        String schema = openGaussDataBaseController.getSchema(poolDTOBean.getPollName(), sql);
        List<Map> list = new ArrayList<>();
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("schemaname", "db4ai");
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("schemaname", "public");
        Map<String, String> map3 = new LinkedHashMap<>();
        map3.put("schemaname", "pg_catalog");
        Map<String, String> map4 = new LinkedHashMap<>();
        map4.put("schemaname", "information_schema");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        String toJSONString = JSON.toJSONString(list);
        assertEquals(toJSONString, schema);
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }

    @Test
    void getTables() throws Exception {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        String sql = "SELECT tablename FROM pg_tables WHERE schemaname = 'public'";
        String tables = openGaussDataBaseController.getTables(poolDTOBean.getPollName(), sql);
        List<Map> list = new ArrayList<>();
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("tablename", "student");
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("tablename", "tbl_001");
        list.add(map1);
        list.add(map2);
        String toJSONString = JSON.toJSONString(list);
        assertEquals(toJSONString, tables);
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }

    @Test
    void getSourceData() throws Exception{
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        String sql = "select * from student where id < 1";
        String sourceData = openGaussDataBaseController.getSourceData(poolDTOBean.getPollName(), sql);
        List<Map> list = new ArrayList<>();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 0);
        map.put("success_percent", 0.6917817);
        map.put("failure_percent", 0.44186792);
        map.put("unknown_percent", 0.9158162);
        list.add(map);
        String toJSONString = JSON.toJSONString(list);
        assertEquals(toJSONString, sourceData);
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }

    @Test
    void showSourceData() throws Exception{
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        String sql = "select * from student";
        String sourceData = openGaussDataBaseController.showSourceData(poolDTOBean.getPollName(), sql, 0, 1);
        List<Map> list = new ArrayList<>();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 0);
        map.put("success_percent", 0.6917817);
        map.put("failure_percent", 0.44186792);
        map.put("unknown_percent", 0.9158162);
        list.add(map);
        String toJSONString = JSON.toJSONString(list);
        assertEquals(toJSONString, sourceData);
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }

    @Test
    void getCount() throws Exception{
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        String sql = "select count(*) from student";
        String count = openGaussDataBaseController.getCount(poolDTOBean.getPollName(), sql);
        List<Map> list = new ArrayList<>();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("count", 1);
        list.add(map);
        String toJSONString = JSON.toJSONString(list);
        assertEquals(toJSONString, count);
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }
}