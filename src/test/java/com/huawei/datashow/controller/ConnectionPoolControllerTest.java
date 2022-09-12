package com.huawei.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.service.ConnectionPoolService;
import com.huawei.datashow.util.MyException;
import com.huawei.datashow.util.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class ConnectionPoolControllerTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConnectionPoolController connectionPoolController;

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

    @Test
    void now() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        Set<String> keySet = ds.getDataSources().keySet();
        Assertions.assertEquals(JSON.toJSONString(keySet), connectionPoolController.now());
    }

    @Test
    void addHikariCP() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        Result<Object> result = connectionPoolController.addHikariCP(poolDTOBean);
        Assertions.assertEquals(Result.OK(), result);
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }

    @Test
    void addHikariCP_when_fail() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        Result<Object> result = connectionPoolController.addHikariCP(poolDTOBean);
        Assertions.assertEquals("[Invalid username/password]", result.getMessage());
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }


    @Test
    void removeHikariCP() throws MyException, FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        Assertions.assertEquals("success", connectionPoolController.removeHikariCP(poolDTOBean.getPollName()));
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }
}