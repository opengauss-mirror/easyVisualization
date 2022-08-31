package com.huawei.datashow.service;

import com.huawei.datashow.bean.DataSourceEditBean;
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
    public void saveDataSource(String pollName, String sql, String dataSourceName) throws IOException;

    /**
     * Read source data from local file
     * @param dataSourceName
     * @param startIndex
     * @param limit
     * @return
     */
    public String readDataSource(String dataSourceName, int startIndex, int limit) throws IOException;

    /**
     * Get data source size
     * @param dataSourceName
     * @return
     * @throws IOException
     */
    public int getDataSourceSize(String dataSourceName) throws IOException;

    /**
     * Remove data source
     * @param dataSourceName
     * @return
     */
    public void removeDataSource(String dataSourceName) throws IOException;

    /**
     * Get data source list
     * @return
     */
    public String getDataSourceList();

    /**
     * Remove columns or rows
     * @param dataSourceEditBean
     */
    public void editDataSource(String dataSourceName, DataSourceEditBean dataSourceEditBean) throws IOException;

    public void saveEditDataSource(String dataSourceName) throws IOException;
}
