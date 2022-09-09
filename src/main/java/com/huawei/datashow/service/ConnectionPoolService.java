package com.huawei.datashow.service;

import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.util.MyException;


public interface ConnectionPoolService
{
    String getConnectionPoolsNow();
    void addHikariCP(ConnectionPoolDTOBean dto) throws MyException;
    String removeHikariCP(String poolName);
}
