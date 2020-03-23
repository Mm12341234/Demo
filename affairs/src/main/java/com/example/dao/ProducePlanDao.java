package com.example.dao;

import com.example.entity.ProducePlanEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@Repository
public interface ProducePlanDao extends BaseDao<ProducePlanEntity>{


    List<ProducePlanEntity> queryListByMap(String parts);

    List<String> queryPartList();

    int queryDemandNum(String parts);

    List<ProducePlanEntity> getDataByWeek(int year,int week);
}