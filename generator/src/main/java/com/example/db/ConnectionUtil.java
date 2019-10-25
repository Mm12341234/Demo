package com.example.db;



import com.example.entity.ColumnInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Liumq
 * @Date   2019/05/23
 * @Describe 数据库的连接，存放表格的基本信息，列，类型等相关信息。
 */
public class ConnectionUtil {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * 初始化数据库连接
     *
     * @return
     */
//    public boolean initConnection() {
//        try {
//            Class.forName(DriverFactory.getDriver(ConfigUtil.getConfiguration().getDb().getUrl()));
//            String url = ConfigUtil.getConfiguration().getDb().getUrl();
//            String username = ConfigUtil.getConfiguration().getDb().getUsername();
//            String password = ConfigUtil.getConfiguration().getDb().getPassword();
//            connection = DriverManager.getConnection(url, username, password);
//            return true;
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * 获取表结构数据
     *
     * @param tableName 表名
     * @return 包含表结构数据的列表
     */
    public List<ColumnInfo> getMetaData(String tableName) throws SQLException {
        /**
         *  1、java.sql.ResultSet接口表示一个数据库查询的结果集。一个ResultSet对象具有一个游标指向当前行的结果集。
         *     最初，光标被置于第一行之前。调用 next() 方法将光标移动到下一行；因为该方法在 ResultSet 对象没有下
         *     一行时返回 false。
         *  2、getMetaData() 获得表结构
         */
        ResultSet tempResultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
        String primaryKey = null;

        if (tempResultSet.next()) {
            primaryKey = tempResultSet.getObject(4).toString();
        }
        List<ColumnInfo> columnInfos = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "SELECT * FROM " + tableName + " WHERE 1 != 1";
        resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            ColumnInfo info;
            if (metaData.getColumnName(i).equals(primaryKey)) {
                info = new ColumnInfo(metaData.getColumnName(i), metaData.getColumnType(i), true);
            } else {
                info = new ColumnInfo(metaData.getColumnName(i), metaData.getColumnType(i), false);
            }
            columnInfos.add(info);
        }
        statement.close();
        resultSet.close();
        return columnInfos;
    }

//    public void close() {
//        try {
//            if (!connection.isClosed()) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
