package com.huawei.datashow.mapper;


import java.util.List;
import java.util.Map;

public interface OpenGaussDataBaseMapper
{
    List<Map> customSql(String sql);
}
