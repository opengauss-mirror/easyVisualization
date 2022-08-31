package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.util.Set;


@Service
public class ConnectionPoolServiceImpl implements ConnectionPoolService
{
    @Autowired
    private DataSource dataSource;
    @Autowired
    private HikariDataSourceCreator hikariDataSourceCreator;
    @Autowired
    private OpenGaussDataBaseService openGaussDataBaseService;

    @Override
    public String getConnectionPoolsNow()
    {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        Set<String> keySet = ds.getDataSources().keySet();
        String ConnectionPoolsNowJson = JSON.toJSONString(keySet);
        return ConnectionPoolsNowJson;
    }

    @Override
    public String addHikariCP(ConnectionPoolDTOBean dto)
    {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        dataSourceProperty.setLazy(true);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPollName(), dataSource);
        try
        {
            openGaussDataBaseService.getSourceData(dto.getPollName(),"select datname from pg_database");
        }
        catch(Exception e)
        {
            ds.getDataSources().remove(dto.getPollName());
            return "fail";
        }
        return "success";
    }

    @Override
    public String removeHikariCP(String poolName)
    {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(poolName);
        return "success";
    }
}
