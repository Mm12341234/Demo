package com.example.db;

/**
 * @Author Liumq
 * @Date   2019/05/23
 */
public class DriverFactory {
    private final static String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
    private final static String DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
    private final static String DRIVER_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static String getDriver(String url) {
        if (url.contains("mysql")) {
            return DRIVER_MYSQL;
        }
        if (url.contains("oracle")) {
            return DRIVER_ORACLE;
        }
        if (url.contains("sqlserver")) {
            return DRIVER_SQLSERVER;
        }
        return null;
    }

}
