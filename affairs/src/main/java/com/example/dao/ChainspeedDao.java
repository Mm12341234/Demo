package com.example.dao;

import com.example.entity.ChainspeedEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@Repository
public interface ChainspeedDao extends BaseDao<ChainspeedEntity>{


    List<ChainspeedEntity> queryAll();
}