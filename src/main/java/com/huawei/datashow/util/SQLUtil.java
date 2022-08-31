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
        String tableName = getColumnName(sql);
        String countSql = sql.replace(tableName, "count(*)");

        if (countSql.lastIndexOf("ORDER") != -1) {
            countSql = countSql.substring(0, countSql.lastIndexOf("ORDER"));
        }

        if (sql.lastIndexOf("GROUP") != -1) {
            countSql = countSql.substring(0, countSql.lastIndexOf("GROUP"));
        }
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
