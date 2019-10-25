package com.example.service.impl;


import com.example.dao.SysGeneratorDao;
import com.example.invoker.SingleInvoker;
import com.example.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service("sysGeneratorService")
public class SysGeneratorServiceImpl implements SysGeneratorService {

    @Autowired
    private SingleInvoker singleInvoker;
    @Autowired
    private SysGeneratorDao sysGeneratorDao;

    /**
     * 查询所有table
     */
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
//        if ("ORACLE".equals(Constant.USE_DATA)) {
//            List<Map<String, Object>> list = sysOracleGeneratorDao.queryList(map);
//
//            //oracle需转为驼峰命名
//            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
//            for (Map<String, Object> stringObjectMap : list) {
//                Map<String, Object> objectMap = new HashMap<String, Object>();
//                for (String key : stringObjectMap.keySet()) {
//                    String mapKey = StringUtils.lineToHump(key);
//                    objectMap.put(mapKey, stringObjectMap.get(key));
//                }
//                mapList.add(objectMap);
//            }
//            return mapList;
//        }
        return sysGeneratorDao.queryList(map);
    }


    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

//        Invoker invoker = new SingleInvoker.Builder()
        for (String tableName : tableNames) {
            singleInvoker.setTableName(tableName);
            singleInvoker.setClassName(columnToJava(tableName));
            singleInvoker.execute(zip);
        }

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

}


