package com.huawei.datashow.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class QueryBean {
    private String pollName;
    private String sql;
    private String tableName;
    private String dataSourceName;
}

