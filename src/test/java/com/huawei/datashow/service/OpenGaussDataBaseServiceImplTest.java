package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.bean.QueryBean;
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
class OpenGaussDataBaseServiceImplTest {

    @Autowired
    private OpenGaussDataBaseService openGaussDataBaseServiceImpl;

    @Autowired
    private ConnectionPoolService connectionPoolServiceImpl;
    ConnectionPoolDTOBean poolDTOBean = new ConnectionPoolDTOBean();

    String sql = "SELECT id,success_percent,failure_percent,unknown_percent FROM student WHERE id < '1'";
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
    void getSourceData() throws Exception {
        QueryBean queryBean = new QueryBean();
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        List<Map> list = new ArrayList<>();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 0);
        map.put("success_percent", 0.6917817);
        map.put("failure_percent", 0.44186792);
        map.put("unknown_percent", 0.9158162);
        list.add(map);
        String toJSONString = JSON.toJSONString(list);
        queryBean.setDataSourceName(dataSourceName);
        queryBean.setSql(sql);
        queryBean.setTableName("student");
        queryBean.setPollName(poolDTOBean.getPollName());
        assertEquals(toJSONString, openGaussDataBaseServiceImpl.getSourceData(queryBean.getPollName(), queryBean.getSql()));
    }
}