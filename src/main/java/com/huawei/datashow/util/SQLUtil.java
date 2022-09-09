package com.huawei.datashow.util;

/**
 * Handle sql
 */
public class SQLUtil {
    /**
     * Get tables
     * @param sql
     * @return
     */
    public static String getColumnName(String sql) {
        int startIndex = 7;
        int endIndex = sql.lastIndexOf("FROM") - 1;
        String tableName = sql.substring(startIndex, endIndex);
        return tableName;
    }

    /**
     * Trans sql to a count sal
     * @param sql
     * @return
     */
    public static String getCountSql(String sql) {
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        return countSql;
    }

    /**
     * Add 'limit' to sql
     * @param sql
     * @param startIndex
     * @param limit
     * @return
     */
    public static String getLimitSql(String sql, int startIndex, int limit) {
        String limitSql = sql + " limit " + startIndex + "," + limit;
        return limitSql;
    }
}
