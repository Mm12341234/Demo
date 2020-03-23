package com.example.service.impl;

import com.example.dao.TbTokenDao;
import com.example.entity.TbTokenEntity;
import com.example.service.TbTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2019-11-05
 */
@Service
public class TbTokenServiceImpl implements TbTokenService {
    @Autowired
    private TbTokenDao tbTokenDao;

    @Override
    public List<TbTokenEntity> queryList(Map<String, Object> map) {
         return tbTokenDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
         return tbTokenDao.queryTotal(map);
    }

    @Override
    public TbTokenEntity queryObject(Long userId) {
         return tbTokenDao.queryObject(userId);
    }

    @Override
    public int save(TbTokenEntity tbToken) {
         return tbTokenDao.save(tbToken);
    }

    @Override
    public int update(TbTokenEntity tbToken) {
         return tbTokenDao.update(tbToken);
    }

    @Override
    public int delete(Long userId) {
         return tbTokenDao.delete(userId);
    }

    @Override
    public int deleteBatch(Long[] userIds) {
         return tbTokenDao.deleteBatch(userIds);
    }
}
