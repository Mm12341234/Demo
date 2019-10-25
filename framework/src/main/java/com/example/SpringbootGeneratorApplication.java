package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

//@MapperScan("com.example.springbootgenerator.dao")
//@SpringBootApplication
//public class SpringbootGeneratorApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(SpringbootGeneratorApplication.class, args);
//    }
//
//}

/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
@MapperScan("com.example.dao")
@SpringBootApplication
public class SpringbootGeneratorApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(SpringbootGeneratorApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGeneratorApplication.class, args);
    }
}