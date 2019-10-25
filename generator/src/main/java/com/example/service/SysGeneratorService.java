package com.example.service;

import java.util.List;
import java.util.Map;

public interface SysGeneratorService {

    /**
     * 查询所有table名
     */
    List<Map<String, Object>> queryList(Map<String, Object> map);
    /**
     * 生成代码
     */
    byte[] generatorCode(String[] tableNames);
}
