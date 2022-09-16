package com.huawei.datashow.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SQLUtilTest {

    @Test
    void getTableName() {
        String sql = "SELECT * FROM ";
        String tableName = SQLUtil.getColumnName(sql);
        String replace = sql.replace(tableName, "count(" + tableName + ")");
        Assertions.assertEquals(tableName, "*");
        Assertions.assertEquals("SELECT count(*) FROM ", replace);
    }

    @Test
    void getCountSql() {
        String sql = "SELECT AVG(id,x,y,z) FROM tpcc_3d_data WHERE y > '0.9' GROUP BY id ORDER BY x";
        String countSql = SQLUtil.getCountSql(sql);
        Assertions.assertEquals("SELECT COUNT(*) FROM (SELECT AVG(id,x,y,z) FROM tpcc_3d_data WHERE y > '0.9' GROUP BY id ORDER BY x)", countSql);
    }

    @Test
    void getLimitSql() {
        String sql = "SELECT id,x FROM tpcc_3d_data WHERE x < '0.5' ORDER BY x";
        String limitSql = SQLUtil.getLimitSql(sql, 0, 10);
        Assertions.assertEquals("SELECT id,x FROM tpcc_3d_data WHERE x < '0.5' ORDER BY x limit 0,10", limitSql);
    }

}