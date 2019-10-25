package com.example.dao;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Liumq
 * @email 1600148841@qq.com
 * @date 2019/10/24
 */
public interface BaseDao<T> {

    int save(T t);

    void save(Map<String, Object> map);

    void saveBatch(List<T> list);

    int update(T t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    T queryObject(Object id);

    List<T> queryList(Map<String, Object> map);

    List<T> queryList(Object id);

    int queryTotal(Map<String, Object> map);

    int queryTotal();
}
