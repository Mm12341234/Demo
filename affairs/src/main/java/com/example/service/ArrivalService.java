package com.example.service;

import com.example.entity.ArrivalEntity;
import com.example.fun0.DemandArrivalVo;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
public interface ArrivalService {

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<ArrivalEntity> queryList(Map<String, Object> map);

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
    ArrivalEntity queryObject(Integer id);

    /**
    * 保存实体
    *
    * @param Arrival 实体
    * @return 保存条数
    */
    int save(ArrivalEntity arrival);

    /**
    * 根据主键更新实体
    *
    * @param Arrival 实体
    * @return 更新条数
    */
    int update(ArrivalEntity arrival);

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


    List<ArrivalEntity> queryAll();

    void test(String parts);

    void splitPointNum();

    List<String> getAllParts();

    DemandArrivalVo getArrivalPartsDetail(String parts);

    void getData(int year,int week);
}
