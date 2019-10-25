package com.example.utils;

import com.example.entity.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.URL;

/**
 * @Author Liumq
 * @Date   2019/05/23
 * @describe 主要是获取generator.yaml文件一些基本信息，比如包名，作者名，数据库连接等。
 */
public class ConfigUtil {
    private static Configuration configuration;

    static {
        URL url = ConfigUtil.class.getClassLoader().getResource("generator.yaml");
        if (!url.getPath().contains("jar")) { // 用户未提供配置文件
            System.err.println("Can not find file named 'generator.yaml' at resources path, please make sure that you have defined that file.");
            System.exit(0);
        } else {
            InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream("generator.yaml");
            Yaml yaml = new Yaml();
            configuration = yaml.loadAs(inputStream, Configuration.class);
        }
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

}
