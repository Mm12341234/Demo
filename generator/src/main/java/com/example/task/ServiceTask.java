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
 * Author 刘铭清
 * Date   2019/10/12
 */
public class ServiceTask extends AbstractTask {

    public ServiceTask(String className) {
        super(className);
    }

    public ServiceTask(String className, ColumnEntity primaryKey) {
        super(className,primaryKey);
    }

    @Override
    public void run(ZipOutputStream zip) throws IOException, TemplateException {
        // 生成Service填充数据
        Map<String, Object> serviceData = new HashMap<>();
        serviceData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        serviceData.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getService());
        serviceData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        serviceData.put("DaoPackageName", ConfigUtil.getConfiguration().getPath().getDao());
        serviceData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        serviceData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        serviceData.put("ClassName", className);
        serviceData.put("EntityName", StringUtil.firstToLowerCase(className));
        serviceData.put("PrimaryKey",primaryKey);
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getService());
        String fileName;
        if (StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getInterf())) { // 表示不生成Service接口文件
            serviceData.put("Impl", "");
            serviceData.put("Override", "");
            serviceData.put("InterfaceImport", "");
            fileName = className + "Service.java";
            System.out.println("Generating " + className + "Service.java");
        } else {
            serviceData.put("Impl", "Impl implements " + className + "Service");
            serviceData.put("Override", "\n    @Override");
            serviceData.put("InterfaceImport", "import " + ConfigUtil.getConfiguration().getPackageName() + ConfigUtil.getConfiguration().getPath().getInterf() + "." + className + "Service;");
            fileName = className + "ServiceImpl.java";
            System.out.println("Generating " + className + "ServiceImpl.java");
        }
        // 生成Service文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_SERVICE, serviceData, filePath + fileName,zip);
    }
}
