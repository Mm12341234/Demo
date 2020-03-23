package com.example.service.impl;

import com.example.dao.ArrivalDao;
import com.example.dao.ChainspeedDao;
import com.example.dao.ProducePlanDao;
import com.example.entity.ArrivalEntity;
import com.example.entity.ChainspeedEntity;
import com.example.entity.ProducePlanEntity;
import com.example.fun0.DemandArrivalVo;
import com.example.fun0.SolveSplit;
import com.example.service.ArrivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@Service
public class ArrivalServiceImpl implements ArrivalService {
    @Autowired
    private ArrivalDao arrivalDao;
    @Autowired
    private ProducePlanDao producePlanDao;
    @Autowired
    private ChainspeedDao chainspeedDao;

    @Override
    public List<ArrivalEntity> queryList(Map<String, Object> map) {
         return arrivalDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
         return arrivalDao.queryTotal(map);
    }

    @Override
    public ArrivalEntity queryObject(Integer id) {
         return arrivalDao.queryObject(id);
    }

    @Override
    public int save(ArrivalEntity arrival) {
         return arrivalDao.save(arrival);
    }

    @Override
    public int update(ArrivalEntity arrival) {
         return arrivalDao.update(arrival);
    }

    @Override
    public int delete(Integer id) {
         return arrivalDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
         return arrivalDao.deleteBatch(ids);
    }

    @Override
    public List<ArrivalEntity> queryAll() {
        String parts="";
        List<ArrivalEntity> list=arrivalDao.queryAll(parts);
        return list;
    }

    @Override
    public void test(String parts) {
        List<ArrivalEntity> arrivalList = arrivalDao.queryAll(parts);
        List<ProducePlanEntity> planEntityList=producePlanDao.queryListByMap(parts);
        List<ChainspeedEntity> chainspeedEntityList=chainspeedDao.queryAll();
        //整理数据
        SolveSplit solveSplit=new SolveSplit();
        solveSplit.solve(arrivalList,planEntityList,chainspeedEntityList);
    }

    @Override
    public void splitPointNum(){

        //根据周期获取多落点的拆分
    }

    @Override
    public List<String> getAllParts() {
        return producePlanDao.queryPartList();
    }

    @Override
    public DemandArrivalVo getArrivalPartsDetail(String parts) {
        //获取某个零件的需求总数
        Integer demandNum=producePlanDao.queryDemandNum(parts);
        Integer arrivalNum=arrivalDao.queryArrivalNum(parts);
        DemandArrivalVo demandArrival=new DemandArrivalVo();
        demandArrival.setArrivalNum(arrivalNum);
        demandArrival.setDemandNum(demandNum);
        return demandArrival;
    }

    @Override
    public void getData(int year,int week) {
        List<ProducePlanEntity> planEntityList= producePlanDao.getDataByWeek(year,week);
        List<ArrivalEntity> arrivalEntityList=arrivalDao.getDataByWeek(year,week);
        List<ChainspeedEntity> chainspeedEntityList=chainspeedDao.queryAll();
        //循环
        List<ProducePlanEntity> planList = new ArrayList<>();
        Map<String,List<ArrivalEntity>> arrivalMap=new HashMap<>();
        String parts="";
        for(ArrivalEntity entity:arrivalEntityList){
            int flag=0;
            for(Map.Entry<String, List<ArrivalEntity>> entry:arrivalMap.entrySet()){
                if(entity.getParts().equals(entry.getKey())){
                    entry.getValue().add(entity);
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                List<ArrivalEntity> list=new ArrayList<>();
                list.add(entity);
                arrivalMap.put(entity.getParts(),list);
            }
        }
        for(ProducePlanEntity entity:planEntityList){
            if(parts.equals("")){
                parts=entity.getParts();
            } else if(parts.equals(entity.getParts())){
                planList.add(entity);
            }else{
                List<ArrivalEntity> arrivalEntities=new ArrayList<>();
                for(Map.Entry<String, List<ArrivalEntity>> entry:arrivalMap.entrySet()){
                    if(entry.getKey().equals(parts)){
                        arrivalEntities=entry.getValue();
                        break;
                    }
                }
                SolveSplit solveSplit=new SolveSplit();
                solveSplit.solve(arrivalEntities,planEntityList,chainspeedEntityList);
                parts=entity.getParts();
                planList=new ArrayList<>();
                planList.add(entity);
            }
        }
        //整理数据
        return;
    }
}
