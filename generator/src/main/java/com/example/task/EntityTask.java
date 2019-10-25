package com.example.task;


import com.example.entity.ColumnEntity;
import com.example.task.base.AbstractTask;
import com.example.utils.ConfigUtil;
import com.example.utils.FileUtil;
import com.example.utils.FreemarketConfigUtils;
import com.example.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Author Liumq
 * @Date   2019-06-06
 */
public class EntityTask extends AbstractTask {

    public EntityTask(String className, List<ColumnEntity> columnList){
        super(className,columnList);
    }

    @Override
    public void run(ZipOutputStream zip) throws IOException, TemplateException {
        // 生成Entity填充数据
        System.out.println("Generating " + className + ".java");
        Map<String, Object> entityData = new HashMap<>();
        entityData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        entityData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        entityData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        entityData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        entityData.put("ClassName", className);
        entityData.put("columns",columnList);
        entityData.put("comments","");
        entityData.put("pathName",className.toLowerCase());
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getEntity());
        String fileName = className + "Entity.java";
        // 生成Entity文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_ENTITY, entityData, filePath + fileName,zip);
    }
}
