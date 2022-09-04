package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.huawei.datashow.mapper.OpenGaussDataBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpenGaussDataBaseServiceImpl implements OpenGaussDataBaseService
{
    @Autowired
    private OpenGaussDataBaseMapper openGaussDataBaseMapper;

    private ValueFilter filter = (obj, s, v) -> {
        if (v == null){
            return "数据为空";
        }
        return v;
    };


    @Override
    public String getSourceData(String pollName, String sql) {
        DynamicDataSourceContextHolder.push(pollName);
        try {
            List<Map> data = openGaussDataBaseMapper.customSql(sql);
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            String json = JSON.toJSONString(data, filter, SerializerFeature.WriteDateUseDateFormat);
            return json;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
