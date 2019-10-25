package com.example.invoker.base;

import com.example.db.ConnectionUtil;
import com.example.entity.ColumnEntity;
import com.example.entity.ColumnInfo;
import com.example.task.base.AbstractTask;
import com.example.utils.TaskQueue;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipOutputStream;

/**
 * @Author Liumq
 * @Date   2019-05-29
 * @Describe 一个抽象类，定义了表格存放的基本信息，连接的信息。
 */
public abstract class AbstractInvoker implements Invoker {
    protected String tableName;
    protected String className;
    protected String parentTableName;
    protected String parentClassName;
    protected String foreignKey;
    protected String relationalTableName;
    protected String parentForeignKey;
    protected List<ColumnEntity> columnList;
    protected List<ColumnInfo> tableInfos;
    //表的主键
    protected ColumnEntity primaryKey;
    protected List<ColumnInfo> parentTableInfos;
    protected ConnectionUtil connectionUtil = new ConnectionUtil();
    protected TaskQueue taskQueue = new TaskQueue();
    private ExecutorService executorPool = Executors.newFixedThreadPool(6);


    protected abstract void getTableInfos() throws SQLException;

    protected abstract void initTasks();

    private void initDataSource() throws Exception {
        getTableInfos();
    }

    @Override
    // 生成代码的核心function
    public void execute( ZipOutputStream zip) {
        try {
            initDataSource();//得到表格列的基本信息
            initTasks();
            while (!taskQueue.isEmpty()) {
                AbstractTask task = taskQueue.poll();
//                executorPool.execute(() -> {
//                    try {
//                        task.run();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TemplateException e) {
//                        e.printStackTrace();
//                    }
//                });
                task.run(zip);
            }
            executorPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setRelationalTableName(String relationalTableName) {
        this.relationalTableName = relationalTableName;
    }

    public void setParentForeignKey(String parentForeignKey) {
        this.parentForeignKey = parentForeignKey;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClassName() {
        return className;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public String getRelationalTableName() {
        return relationalTableName;
    }

    public String getParentForeignKey() {
        return parentForeignKey;
    }

    public ColumnEntity getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ColumnEntity primaryKey) {
        this.primaryKey = primaryKey;
    }
}
