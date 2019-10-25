package com.example.entity;

import com.example.utils.StringUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Liumq
 * @Date   2019/05/23
 */
@Data
public class ColumnInfo implements Serializable {
    private String columnName; // 列名
    private int type; // 类型代码
    private String propertyName; // 属性名
    private boolean isPrimaryKey; // 是否主键

    public ColumnInfo() {

    }

    public ColumnInfo(String columnName, int type, boolean isPrimaryKey) {
        this.columnName = columnName;
        this.type = type;
        this.propertyName = StringUtil.columnName2PropertyName(columnName);
        this.isPrimaryKey = isPrimaryKey;
    }

}
