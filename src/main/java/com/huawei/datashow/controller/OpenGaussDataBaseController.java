package com.huawei.datashow.controller;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.huawei.datashow.bean.smbms_3DdataBean;
import com.huawei.datashow.mapper.OpenGaussDataBaseMapper;
import com.huawei.datashow.service.OpenGaussDataBaseService;
import com.huawei.datashow.util.SQLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class OpenGaussDataBaseController
{
    @Autowired
    private OpenGaussDataBaseService openGaussDataBaseServiceImpl;

    @Autowired
    private OpenGaussDataBaseMapper openGaussDataBaseMapper;

    @GetMapping("/getSchema")
    public String getSchema(@RequestParam("pollName") String pollName,
                            @RequestParam("sql") String sql) throws Exception {
        return openGaussDataBaseServiceImpl.getSourceData(pollName, sql);
    }

    /**
     * Get tables
     * @param pollName
     * @param sql SELECT tablename FROM pg_tables WHERE schemaname = 'xxx'
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/getTables")
    public String getTables(@RequestParam("pollName") String pollName,
                            @RequestParam("sql") String sql) throws Exception {
        return openGaussDataBaseServiceImpl.getSourceData(pollName, sql);
    }

    /**
     * Get source data from table
     * @param pollName
     * @param sql
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/getSourceData")
    public String getSourceData(@RequestParam("pollName") String pollName,
                                @RequestParam("sql") String sql) throws Exception {
        return openGaussDataBaseServiceImpl.getSourceData(pollName,sql);
    }

    /**
     * Get data in the database in pages
     * @param pollName
     * @param sql
     * @param startIndex
     * @param limit
     * @return
     */
    @GetMapping("/showSourceData")
    public String showSourceData(@RequestParam("pollName") String pollName,
                                 @RequestParam("sql") String sql,
                                 @RequestParam("startIndex") int startIndex,
                                 @RequestParam("limit") int limit) throws Exception {
        String limitSql = SQLUtil.getLimitSql(sql, startIndex, limit);
        return openGaussDataBaseServiceImpl.getSourceData(pollName, limitSql);
    }

    @GetMapping("/getCount")
    public String getCount(@RequestParam("pollName") String pollName,
                           @RequestParam("sql") String sql) throws Exception {
        String countSql = SQLUtil.getCountSql(sql);
        return openGaussDataBaseServiceImpl.getSourceData(pollName, countSql);
    }


}
