package com.example.service.impl;

import com.example.dao.ChainspeedDao;
import com.example.entity.ChainspeedEntity;
import com.example.service.ChainspeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@Service
public class ChainspeedServiceImpl implements ChainspeedService {
    @Autowired
    private ChainspeedDao chainspeedDao;

    @Override
    public List<ChainspeedEntity> queryList(Map<String, Object> map) {
         return chainspeedDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
         return chainspeedDao.queryTotal(map);
    }

    @Override
    public ChainspeedEntity queryObject(Integer id) {
         return chainspeedDao.queryObject(id);
    }

    @Override
    public int save(ChainspeedEntity chainspeed) {
         return chainspeedDao.save(chainspeed);
    }

    @Override
    public int update(ChainspeedEntity chainspeed) {
         return chainspeedDao.update(chainspeed);
    }

    @Override
    public int delete(Integer id) {
         return chainspeedDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
         return chainspeedDao.deleteBatch(ids);
    }

    @Override
    public List<ChainspeedEntity> queryAll() {
        return chainspeedDao.queryAll();
    }
}
