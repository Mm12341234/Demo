package com.example.service;

import com.example.entity.TbTokenEntity;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2019-11-05
 */
public interface TbTokenService {

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<TbTokenEntity> queryList(Map<String, Object> map);

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
    TbTokenEntity queryObject(Long userId);

    /**
    * 保存实体
    *
    * @param TbToken 实体
    * @return 保存条数
    */
    int save(TbTokenEntity tbToken);

    /**
    * 根据主键更新实体
    *
    * @param TbToken 实体
    * @return 更新条数
    */
    int update(TbTokenEntity tbToken);

    /**
    * 根据主键删除
    *
    * @param userId
    * @return 删除条数
    */
    int delete(Long userId);

    /**
    * 根据主键批量删除
    *
    * @param userIds
    * @return 删除条数
    */
    int deleteBatch(Long[] userIds);

}
