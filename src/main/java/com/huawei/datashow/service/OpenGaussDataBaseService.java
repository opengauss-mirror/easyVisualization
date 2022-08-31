package com.huawei.datashow.service;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface OpenGaussDataBaseService
{

    /**
     * Get source data from database
     * @param pollName
     * @param sql
     * @return
     * @throws JsonProcessingException
     */
    String getSourceData(String pollName, String sql) throws JsonProcessingException;
}
