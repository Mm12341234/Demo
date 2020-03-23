package com.example.dao;

import com.example.entity.ArrivalEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@Repository
public interface ArrivalDao extends BaseDao<ArrivalEntity>{


    List<ArrivalEntity> queryAll(String parts);

    int queryArrivalNum(String parts);

    List<ArrivalEntity> getDataByWeek(int year,int week);
}