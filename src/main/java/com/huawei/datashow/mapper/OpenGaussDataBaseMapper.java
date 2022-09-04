package com.huawei.datashow.mapper;

import com.huawei.datashow.bean.smbms_3DdataBean;

import java.util.List;
import java.util.Map;

public interface OpenGaussDataBaseMapper
{
    List<Map> customSql(String sql);
    void insert(smbms_3DdataBean bean);
}
