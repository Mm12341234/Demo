package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class LoginController {

    @RequestMapping("/index")
    public String index(){
        return null;
    }

    public void testlog(){
        //记录客户端请求参数
    }
}
