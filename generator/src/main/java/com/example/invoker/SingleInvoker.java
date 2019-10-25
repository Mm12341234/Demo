package com.example.invoker;

import com.example.dao.SysGeneratorDao;
import com.example.entity.ColumnEntity;
import com.example.entity.ColumnInfo;
import com.example.invoker.base.AbstractInvoker;
import com.example.utils.ConfigUtil;
import com.example.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Liumq
 * @Date   2019-05-28
 * @Desc  获取生成的数据表格基本信息
 *
 */
@Component
public class SingleInvoker extends AbstractInvoker {

    @Autowired
    private SysGeneratorDao sysGeneratorDao;

    @Override
    protected void getTableInfos(){

        //获取表的基本信息
        Map<String,Object> map=new HashMap<>();
        map.put("tableName",tableName);
        List<ColumnInfo> endList=new ArrayList<>();
        List<Map<String, String>> columns=sysGeneratorDao.queryColumns(tableName);

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();

        //将数据表中的详细信息进行java数据转换
        for (Map<String, String> column : columns) {
            ColumnInfo columnInfo=new ColumnInfo();
            if(column.get("columnKey")!=null){
                columnInfo.setPrimaryKey(true);
            }else{
                columnInfo.setPrimaryKey(false);
            }
            columnInfo.setColumnName(column.get("columnName"));
            columnInfo.setPropertyName(column.get("dataType"));
            columnInfo.setType(1);
            endList.add(columnInfo);


            //ssk的封装方法
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

//            列的数据类型，转换成Java类型
            String attrType = ConfigUtil.getConfiguration().getProperty().get(columnEntity.getDataType()).toString();
            columnEntity.setAttrType(attrType);

            //是否主键
            if ("ORACLE".equals(Constant.USE_DATA)) {
                if ((column.get("columnName").equalsIgnoreCase(column.get("columnKey")) &&  primaryKey== null)) {
                    primaryKey=columnEntity;
                }
            } else {
                if (("PRI".equalsIgnoreCase(column.get("columnKey")) && primaryKey == null)) {
                    primaryKey=columnEntity;
                }
            }
            columsList.add(columnEntity);
        }
        tableInfos = endList;
        columnList=columsList;
    }

    @Override
    protected void initTasks() {
        taskQueue.initSingleTasks(className, tableName,columnList,primaryKey);
    }



    /**
     *  内部类，初始化了需要生成代码的表名以及类名。
     */
//    public static class Builder extends AbstractBuilder {
//        private SingleInvoker invoker = new SingleInvoker();
//
//        public Builder setTableName(String tableName) {
//            invoker.setTableName(tableName);
//            return this;
//        }
//
//        public Builder setClassName(String className) {
//            invoker.setClassName(className);
//            return this;
//        }
//
//        @Override
//        public Invoker build() {
//            if (!isParamtersValid()) {
//                return null;
//            }
//            return invoker;
//        }
//
//        @Override
//        public void checkBeforeBuild() throws Exception {
//            if (StringUtil.isBlank(invoker.getTableName())) {
//                throw new Exception("Expect table's name, but get a blank String.");
//            }
//            if (StringUtil.isBlank(invoker.getClassName())) {
//                invoker.setClassName(GeneratorUtil.generateClassName(invoker.getTableName()));
//            }
//        }
//    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

}
