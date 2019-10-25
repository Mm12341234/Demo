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
public class HtmlTask extends AbstractTask {

    public HtmlTask(String className) {
        super(className);
    }

    public HtmlTask(String className, List<ColumnEntity> columnList){
        super(className,columnList);
    }

    @Override
    public void run(ZipOutputStream zip) throws IOException, TemplateException {
        // 生成html填充数据
        System.out.println("Generating " + className + ".html");
        Map<String, Object> htmlData = new HashMap<>();
        htmlData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        htmlData.put("InterfacePackageName", ConfigUtil.getConfiguration().getPath().getHtml());
        htmlData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        htmlData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        htmlData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        htmlData.put("ClassName", className);
        htmlData.put("EntityName", StringUtil.firstToLowerCase(className));
        htmlData.put("columns",columnList);
        htmlData.put("comments","");
        htmlData.put("pathName",className.toLowerCase());
        String filePath = FileUtil.getResourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getHtml());
        String fileName = className.toLowerCase() + ".html";
        // 生成Service接口文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_HTML, htmlData, filePath + fileName,zip);
    }
}
