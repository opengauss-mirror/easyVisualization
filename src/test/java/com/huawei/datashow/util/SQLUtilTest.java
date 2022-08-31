package com.huawei.datashow.util;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;

class SQLUtilTest {

    @Test
    void getTableName() {
        String sql = "SELECT * FROM ";
        String tableName = SQLUtil.getColumnName(sql);
        String replace = sql.replace(tableName, "count(" + tableName + ")");
        System.out.println(tableName);
        System.out.println(replace);
    }

    @Test
    void getCountSql() {
        String sql = "SELECT AVG(id,x,y,z) FROM tpcc_3d_data WHERE y > '0.9' GROUP BY id ORDER BY x";
        String countSql = SQLUtil.getCountSql(sql);
        System.out.println(countSql);
    }

    @Test
    void getLimitSql() {
        String sql = "SELECT id,x FROM tpcc_3d_data WHERE x < '0.5' ORDER BY x";
        String limitSql = SQLUtil.getLimitSql(sql, 0, 10);
        System.out.println(limitSql);
    }

    @Test
    void solution() {

    }
}