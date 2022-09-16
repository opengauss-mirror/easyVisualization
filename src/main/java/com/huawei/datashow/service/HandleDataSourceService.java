package com.huawei.datashow.service;

import com.huawei.datashow.bean.DataSourceEditBean;
import com.huawei.datashow.util.MyException;

import java.io.IOException;


/**
 * handle data source
 */
public interface HandleDataSourceService {
    /**
     * Save source data in local file
     * @param pollName
     * @param sql
     * @param dataSourceName
     * @return
     */
    void saveDataSource(String pollName, String sql, String dataSourceName) throws Exception;

    /**
     * Read source data from local file
     * @param dataSourceName
     * @param startIndex
     * @param limit
     * @return
     */
    String readDataSource(String dataSourceName, int startIndex, int limit) throws IOException;

    /**
     * Get data source size
     * @param dataSourceName
     * @return
     * @throws IOException
     */
    int getDataSourceSize(String dataSourceName) throws IOException;

    /**
     * Remove data source
     * @param dataSourceName
     * @return
     */
    void removeDataSource(String dataSourceName) throws IOException;

    /**
     * Get data source list
     * @return
     */
    String getDataSourceList();

    /**
     * Remove columns or rows
     * @param dataSourceEditBean
     */
    void editDataSource(String dataSourceName, DataSourceEditBean dataSourceEditBean) throws IOException;

    void reloadDataSource(String dataSourceName) throws IOException;

    void saveEditDataSource(String dataSourceName) throws IOException, MyException;

    boolean getEditStatus(String dataSourceName) throws IOException;
}
