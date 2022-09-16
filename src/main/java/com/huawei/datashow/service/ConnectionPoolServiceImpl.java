package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.util.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Set;


@Service
public class ConnectionPoolServiceImpl implements ConnectionPoolService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private HikariDataSourceCreator hikariDataSourceCreator;
    @Autowired
    private OpenGaussDataBaseService openGaussDataBaseService;

    @Override
    public String getConnectionPoolsNow() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        Set<String> keySet = ds.getDataSources().keySet();
        String ConnectionPoolsNowJson = JSON.toJSONString(keySet);
        return ConnectionPoolsNowJson;
    }

    @Override
    public void addHikariCP(ConnectionPoolDTOBean dto) throws MyException {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        dataSourceProperty.setLazy(true);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPollName(), dataSource);
        try {
            openGaussDataBaseService.getSourceData(dto.getPollName(), "select datname from pg_database");
        } catch (Exception e) {
            ds.removeDataSource(dto.getPollName());
            String exception = e.toString();
            if (exception.contains("Invalid username/password")) {
                throw new MyException("[Invalid username/password]");
            } else if (exception.contains("Forbid remote connection with initial user")) {
                throw new MyException("[Forbid remote connection with initial user]");
            } else if (exception.contains("no pg_hba.conf entry")) {
                throw new MyException("[no pg_hba.conf entry for host]");
            } else if (exception.contains("database") && exception.contains("does not exist")) {
                throw new MyException("[database does not exist]");
            } else if (exception.contains("Check that the hostname and port are correct")) {
                throw new MyException("[Check that the hostname and port are correct and " +
                        "that the postmaster is accepting TCP/IP connections]");
            } else {
                throw new MyException("[Invalid input or other]");
            }
        }
    }

    @Override
    public String removeHikariCP(String poolName) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(poolName);
        return "success";
    }
}
