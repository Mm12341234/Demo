package com.example.task;

import com.example.entity.ColumnEntity;
import com.example.entity.ColumnInfo;
import com.example.task.base.AbstractTask;
import com.example.utils.ConfigUtil;
import com.example.utils.FileUtil;
import com.example.utils.FreemarketConfigUtils;
import com.example.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Author 刘铭清
 * Date   2019/10/12
 */
public class MapperTask extends AbstractTask {

    public MapperTask(String className, String tableName, List<ColumnEntity> columnList,ColumnEntity primaryKey){
        super(className,tableName,columnList,primaryKey);
    }

    /**
     * 单表Mapper
     */
//    public MapperTask(String className, String tableName, List<ColumnInfo> infos) {
//        this(tableName, className, null, null, null, infos, null);
//    }

    /**
     * 一对多Mapper
     */
    public MapperTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        this(tableName, className, parentTableName, parentClassName, foreignKey, null, null, tableInfos, parentTableInfos);
    }

    /**
     * 多对多Mapper
     */
    public MapperTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, String parentForeignKey, String relationalTableName, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        super(tableName, className, parentTableName, parentClassName, foreignKey, parentForeignKey, relationalTableName, tableInfos, parentTableInfos);
    }

    @Override
    public void run(ZipOutputStream zip) throws IOException, TemplateException {
        // 生成Mapper填充数据
        System.out.println("Generating " + className + "Mapper.xml");
        Map<String, Object> mapperData = new HashMap<>();
        mapperData.put("PackageName", ConfigUtil.getConfiguration().getPackageName() + "." + ConfigUtil.getConfiguration().getPath().getDao());
        mapperData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        mapperData.put("DaoPackageName", ConfigUtil.getConfiguration().getPath().getDao());
        mapperData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        mapperData.put("ClassName", className);
        mapperData.put("EntityName", StringUtil.firstToLowerCase(className));
        mapperData.put("TableName", tableName);
//        mapperData.put("InsertProperties", GeneratorUtil.generateMapperInsertProperties(tableInfos));
        mapperData.put("PrimaryKey", primaryKey);
//        mapperData.put("WhereId", "#{" + getPrimaryKeyColumnInfo(tableInfos).getPropertyName() + "}");
        mapperData.put("columns",columnList);
        mapperData.put("comments","");
        mapperData.put("pathName",className.toLowerCase());
        mapperData.put("Id", "#{id}");
        String filePath = FileUtil.getResourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getMapper());
        String fileName = className + "Dao.xml";
        // 生成Mapper文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_MAPPER, mapperData, filePath + fileName,zip);
    }

    private ColumnInfo getPrimaryKeyColumnInfo(List<ColumnInfo> list) {
        for (ColumnInfo columnInfo : list) {
            if (columnInfo.isPrimaryKey()) {
                return columnInfo;
            }
        }
        return null;
    }

}
