package com.example.service.impl;

import com.example.dao.ContentDao;
import com.example.dao.TbTokenDao;
import com.example.entity.Content;
import com.example.entity.TbTokenEntity;
import com.example.service.ContentService;
import com.example.service.TbTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author Liumq
 * Date  2019-11-05
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbTokenDao tbTokenDao;
    @Autowired
    private ContentDao contentDao;

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

    @Override
    public void test(TbTokenEntity tbToken) {
        Map<String,Object> map=new HashMap();
        List<Content> list=contentDao.queryList(map);
        List<Content> crateList=new ArrayList<>();
        List<Content> palletList=new ArrayList<>();
        //对周转箱、托盘进行分类
        for(Content entity:list){
            if(entity.getType()==Content.ContentType.crate.getCode()){
                for(int i=0;i<entity.getNum();i++){
                    crateList.add(entity);
                }
            }else{
                for(int i=0;i<entity.getNum();i++){
                    palletList.add(entity);
                }
            }
        }

        for(Content crateEntity:crateList){
            //为周转箱寻找一个最好比例的托盘
            for(Content palletEntity:palletList){
                if((palletEntity.getWidth()/crateEntity.getWidth())==(palletEntity.getDepth()/crateEntity.getDepth())){
                    System.out.println("");
                }
            }
        }
    }
}
