package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.huawei.datashow.DemoApplication;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.util.MyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.sql.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@EnableAutoConfiguration
class ConnectionPoolServiceImplTest {

    @Autowired
    private DataSource dataSource;

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


    @Test()
    void getConnectionPoolsNow() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        Set<String> keySet = ds.getDataSources().keySet();
        String ConnectionPoolsNowJson = JSON.toJSONString(keySet);
        Assertions.assertEquals(ConnectionPoolsNowJson, connectionPoolServiceImpl.getConnectionPoolsNow());
    }


    @Test
    void addHikariCP_fail1() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        try {
            connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        } catch (MyException e) {
            assertThat(e.getMessage(), containsString("[Invalid username/password]"));
        } finally {
            connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
        }
    }

    @Test
    void addHikariCP_fail2() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        try {
            connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        } catch (MyException e) {
            assertThat(e.getMessage(), containsString("[no pg_hba.conf entry for host]"));
        } finally {
            connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
        }
    }

    @Test
    void addHikariCP_fail3() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        try {
            connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        } catch (MyException e) {
            assertThat(e.getMessage(), containsString("[database does not exist]"));
        } finally {
            connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
        }
    }

    @Test
    void addHikariCP_fail4() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        try {
            connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        } catch (MyException e) {
            assertThat(e.getMessage(), containsString("Check that the hostname and port are correct"));
        } finally {
            connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
        }
    }

    @Test
    void addHikariCP_fail5() throws FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        try {
            connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        } catch (MyException e) {
            assertThat(e.getMessage(), containsString("Invalid input or other"));
        } finally {
            connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
        }
    }

    @Test
    void removeHikariCP() throws MyException, FileNotFoundException {
        ConnectionPoolDTOBean poolDTOBean = getPoolDTOBean();
        connectionPoolServiceImpl.addHikariCP(poolDTOBean);
        Assertions.assertEquals("success", connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName()));
        connectionPoolServiceImpl.removeHikariCP(poolDTOBean.getPollName());
    }
}