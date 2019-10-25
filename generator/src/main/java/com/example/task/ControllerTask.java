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
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Author Liumq
 * @Date   2019/05/23
 * @Describe 生成controller.java类
 */
public class ControllerTask extends AbstractTask {


    public ControllerTask(String className,ColumnEntity primaryKey){
        super(className,primaryKey);
    }

    public ControllerTask(String className) {
        super(className);
    }

    @Override
    public void run( ZipOutputStream zip) throws IOException, TemplateException {
        // 生成Controller填充数据
        System.out.println("Generating " + className + "Controller.java");
        Map<String, Object> controllerData = new HashMap<>();
        controllerData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        controllerData.put("ControllerPackageName", ConfigUtil.getConfiguration().getPath().getController());
        if (StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getInterf())) {
            controllerData.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getService());
        } else {
            controllerData.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getInterf());
        }
        controllerData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        controllerData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        controllerData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        controllerData.put("ClassName", className);
        controllerData.put("pathName",className.toLowerCase());
        controllerData.put("EntityName", StringUtil.firstToLowerCase(className));
        controllerData.put("PrimaryKey",primaryKey);
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getController());
        String fileName = className + "Controller.java";
        // 生成Controller文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONTROLLER, controllerData, filePath + fileName,zip);
    }
}
