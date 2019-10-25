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
 * Author 刘铭清
 * Date   2019/10/12
 */
public class JsTask extends AbstractTask {

    public JsTask(String className) {
        super(className);
    }

    public JsTask(String className, List<ColumnEntity> columnList,ColumnEntity primaryKey){
        super(className,columnList,primaryKey);
    }

    @Override
    public void run(ZipOutputStream zip) throws IOException, TemplateException {
        // 生成html填充数据
        System.out.println("Generating " + className + ".js");
        Map<String, Object> jsData = new HashMap<>();
        jsData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        jsData.put("jsPackageName", ConfigUtil.getConfiguration().getPath().getJs());
        jsData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        jsData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        jsData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        jsData.put("ClassName", className);
        jsData.put("EntityName", StringUtil.firstToLowerCase(className));
        jsData.put("columns",columnList);
        jsData.put("comments","");
        jsData.put("pathName",className.toLowerCase());
        jsData.put("PrimaryKey",primaryKey);
        String filePath = FileUtil.getResourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getHtml());
        String fileName = className.toLowerCase() + ".js";
        // 生成Service接口文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_JS, jsData, filePath + fileName,zip);
    }
}
