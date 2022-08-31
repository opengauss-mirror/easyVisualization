package com.huawei.datashow.service;

import com.huawei.datashow.bean.ConnectionPoolDTOBean;


public interface ConnectionPoolService
{
    String getConnectionPoolsNow();
    String addHikariCP(ConnectionPoolDTOBean dto);
    String removeHikariCP(String poolName);
}
