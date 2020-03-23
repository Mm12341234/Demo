package com.example.service.impl;

import com.example.dao.ProducePlanDao;
import com.example.entity.ProducePlanEntity;
import com.example.service.ProducePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@Service
public class ProducePlanServiceImpl implements ProducePlanService {
    @Autowired
    private ProducePlanDao producePlanDao;

    @Override
    public List<ProducePlanEntity> queryList(Map<String, Object> map) {
         return producePlanDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
         return producePlanDao.queryTotal(map);
    }

    @Override
    public ProducePlanEntity queryObject(Integer id) {
         return producePlanDao.queryObject(id);
    }

    @Override
    public int save(ProducePlanEntity producePlan) {
         return producePlanDao.save(producePlan);
    }

    @Override
    public int update(ProducePlanEntity producePlan) {
         return producePlanDao.update(producePlan);
    }

    @Override
    public int delete(Integer id) {
         return producePlanDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
         return producePlanDao.deleteBatch(ids);
    }

    @Override
    public List<ProducePlanEntity> queryListByMap() {
        List<ProducePlanEntity> list=producePlanDao.queryListByMap("");
        return list;
    }
}
