package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * 系统页面视图Controller
 *
 * @author Liumq
 * @email 1600148841@qq.com
 * @date 2019-05-29
 */
@Controller
public class SysPageController {

    /**
     * 视图路径
     *
     * @param module 模块
     * @param url    url
     * @return 页面视图路径
     */
    @RequestMapping("{module}/{url}.html")
    public String page(@PathVariable("module") String module, @PathVariable("url") String url, HttpServletRequest request) {
//        ServletContext servletContext = request.getSession().getServletContext();
//        System.out.println(request.getContextPath());
//        return url+".html";
//        String fileLoaclPath = request.getServletContext().getContextPath();
        return module + "/" + url + ".html";
    }

}
