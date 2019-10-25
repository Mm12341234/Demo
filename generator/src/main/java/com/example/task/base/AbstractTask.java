package com.example.task.base;

import com.example.entity.ColumnEntity;
import com.example.entity.ColumnInfo;
import com.example.utils.ConfigUtil;
import com.example.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @Author Liumq
 * @Date   2019/05/23
 * @Describe 这个抽象类定义了一个table的基本信息，比如表明，对应的类名，各个列。
 */
public abstract class AbstractTask implements Serializable {
    protected String tableName;
    protected String className;
    protected String parentTableName;
    protected String parentClassName;
    protected String foreignKey;
    protected String relationalTableName;
    protected String parentForeignKey;
    protected List<ColumnEntity> columnList;
    protected List<ColumnInfo> tableInfos;
    protected List<ColumnInfo> parentTableInfos;
    //表的主键
    protected ColumnEntity primaryKey;

    /**
     * Controller、Service、Dao
     *
     * @param className
     */
    public AbstractTask(String className) {
        this.className = className;
    }

    public AbstractTask(String className, List<ColumnEntity> columnList) {
        this.className = className;
        this.columnList=columnList;
    }

    /**
     * html
     */
    public AbstractTask(String className, ColumnEntity primaryKey) {
        this.className = className;
        this.primaryKey=primaryKey;
    }


    /**
     * js
     */
    public AbstractTask(String className, List<ColumnEntity> columnList, ColumnEntity primaryKey) {
        this.className = className;
        this.columnList=columnList;
        this.primaryKey=primaryKey;
    }

    /**
     * mappers
     */
    public AbstractTask(String className,String tableName,List<ColumnEntity> columnList,ColumnEntity primaryKey) {
        this.tableName=tableName;
        this.className = className;
        this.columnList=columnList;
        this.primaryKey=primaryKey;
    }

    /**
     * Entity
     *
     * @param className
     * @param parentClassName
     * @param foreignKey
     * @param tableInfos
     */
    public AbstractTask(String className, String parentClassName, String foreignKey, String parentForeignKey, List<ColumnInfo> tableInfos) {
        this.className = className;
        this.parentClassName = parentClassName;
        this.foreignKey = foreignKey;
        this.parentForeignKey = parentForeignKey;
        this.tableInfos = tableInfos;
    }


    /**
     * Mapper
     *
     * @param tableName
     * @param className
     * @param parentTableName
     * @param parentClassName
     * @param foreignKey
     * @param parentForeignKey
     * @param tableInfos
     * @param parentTableInfos
     */
    public AbstractTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, String parentForeignKey, String relationalTableName, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        this.tableName = tableName;
        this.className = className;
        this.parentTableName = parentTableName;
        this.parentClassName = parentClassName;
        this.foreignKey = foreignKey;
        this.parentForeignKey = parentForeignKey;
        this.relationalTableName = relationalTableName;
        this.tableInfos = tableInfos;
        this.parentTableInfos = parentTableInfos;
    }

    public abstract void run( ZipOutputStream zip) throws IOException, TemplateException;

    /**
     *  注解 @Deprecated 意为这个方法或者类不再建议使用。
     */
    @Deprecated
    protected void createFilePathIfNotExists(String filePath) {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPackageName())) { // 用户配置了包名，不进行检测
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) { // 检测文件路径是否存在，不存在则创建
            file.mkdir();
        }
    }

}
