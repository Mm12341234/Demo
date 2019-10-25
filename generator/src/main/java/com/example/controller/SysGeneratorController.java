package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.service.SysGeneratorService;
import com.example.utils.Query;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    @ResponseBody
    @RequestMapping("/list")
    public  List<Map<String, Object>> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        params.put("page",1);
        params.put("limit",10);
        Query query = new Query(params);
        List<Map<String, Object>> list = sysGeneratorService.queryList(query);
//        int total = sysGeneratorService.queryTotal(query);
//
//        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

        return list;
    }

    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String[] tableNames = new String[]{};
//        //获取表名，不进行xss过滤
//        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
//        String tables = orgRequest.getParameter("tables");

        String tables=request.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);
        byte[] data = sysGeneratorService.generatorCode(tableNames);
//        String[] tables={tableName};

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"AutoCode.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
