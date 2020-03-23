package com.example.service;

import com.example.entity.ProducePlanEntity;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
public interface ProducePlanService {

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<ProducePlanEntity> queryList(Map<String, Object> map);

    /**
    * 分页统计总数
    *
    * @param map 参数
    * @return 总数
    */
    int queryTotal(Map<String, Object> map);

    /**
    * 根据主键查询实体
    *
    * @param id 主键
    * @return 实体
    */
    ProducePlanEntity queryObject(Integer id);

    /**
    * 保存实体
    *
    * @param ProducePlan 实体
    * @return 保存条数
    */
    int save(ProducePlanEntity producePlan);

    /**
    * 根据主键更新实体
    *
    * @param ProducePlan 实体
    * @return 更新条数
    */
    int update(ProducePlanEntity producePlan);

    /**
    * 根据主键删除
    *
    * @param id
    * @return 删除条数
    */
    int delete(Integer id);

    /**
    * 根据主键批量删除
    *
    * @param ids
    * @return 删除条数
    */
    int deleteBatch(Integer[] ids);


    List<ProducePlanEntity> queryListByMap();
}
