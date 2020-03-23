package com.example.heuristic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    private List<Car> carList;
    private List<Cage> cageList;

    public static void  main(String[] args){
        Test test=new Test();
        test.initData();
        test.poolCar();
        System.out.println();
    }

    //初始化数据
    public void initData(){
        carList=new ArrayList<>();
        cageList=new ArrayList<>();
        for(int i=0;i<400;i++){
            Cage cage=new Cage();
            if(i<20){
                cage.setHeight(108);
                cage.setWidth(135);
            }else if(i<40){
                cage.setHeight(76);
                cage.setWidth(104);
            }else if(i<60){
                cage.setHeight(135);
                cage.setWidth(108);
            }else if(i<80){
                cage.setHeight(100);
                cage.setWidth(120);
            }else if(i<100){
                cage.setHeight(75);
                cage.setWidth(105);
            }else if(i<120){
                cage.setHeight(108);
                cage.setWidth(135);
            }else if(i<140){
                cage.setHeight(80);
                cage.setWidth(93);
            }else if(i<160){
                cage.setHeight(75);
                cage.setWidth(146);
            }else if(i<180){
                cage.setHeight(60);
                cage.setWidth(135);
            }else if(i<200){
                cage.setHeight(70);
                cage.setWidth(135);
            }else{
                cage.setHeight(75);
                cage.setWidth(104);
            }
            cageList.add(cage);
        }
    }

    //拼车计划
    public void poolCar(){
        Comparator<Cage> byTime = Comparator.comparing(Cage::getWidth);
        //排序后得到的summonsList序列
        cageList= cageList.stream().sorted(byTime).collect(Collectors.toList());
        Car car=new Car();
        for(int i=0;i<cageList.size();i++){
            if(car.getCageList()==null||car.getCageList().size()==0){
                cageList.get(i).getStartPoint().setX(0);
                cageList.get(i).getStartPoint().setY(0);
                cageList.get(i).getEndPoint().setX(cageList.get(i).getWidth());
                cageList.get(i).getEndPoint().setY(cageList.get(i).getHeight());
                car.getCageList().add(cageList.get(i));
                cageList.remove(cageList.get(i));
            }else{
                Boolean test=canPutInCar(car,cageList.get(i));
                if(test==false){
                    carList.add(car);
                    car=new Car();
                }
            }
        }
        System.out.println();
    }

    //判断该网络车是否能装入车中
    public Boolean canPutInCar(Car car,Cage cage){

         List<Cage> upCageList=new ArrayList<>();
        //找到卡车的最低点的几个坐标
        Cage tpCage;
        double x1,x2;
        for(int i=0;i< car.getCageList().size();i++){
            int flag=0;
            tpCage=car.getCageList().get(i);
            for(int j=i+1;j<car.getCageList().size();j++){
                x1=car.getCageList().get(j).getEndPoint().getX()-car.getCageList().get(j).getWidth();
                x2=car.getCageList().get(j).getEndPoint().getX();
                if((tpCage.getEndPoint().getX()>x1&&tpCage.getEndPoint().getX()<=x2)
                        &&tpCage.getEndPoint().getY()<car.getCageList().get(j).getEndPoint().getY()){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                upCageList.add(tpCage);
            }
        }
        //为该网笼选择一个好的位置
        //最右边是否还有空位
        double maxLeft=0;
        int flag=0;
        for(Cage entity:upCageList){
            if(entity.getStartPoint().getY()==0&&maxLeft<entity.getEndPoint().getX()){
                maxLeft=entity.getEndPoint().getX();
                flag=1;
            }
        }
        if(flag==1&&car.getWidth()-maxLeft>cage.getWidth()){
            //查看右边是否有空位
            cage.getStartPoint().setX(maxLeft);
            cage.getStartPoint().setY(0);
            cage.getEndPoint().setX(maxLeft + cage.getWidth());
            cage.getEndPoint().setY(cage.getHeight());
            car.getCageList().add(cage);
        }else{
            //对最高点进行一次排序
            for(int i=0;i<upCageList.size();i++){
                for(int j=i+1;j<upCageList.size()-1;j++){
                    if(upCageList.get(i).getEndPoint().getY()>upCageList.get(j).getEndPoint().getY()){
                        Cage item=upCageList.get(i);
                        upCageList.add(i,upCageList.get(j));
                        upCageList.remove(i+1);
                        upCageList.add(j,item);
                        upCageList.remove(j+1);
                    }
                }
            }
            //挑选高度最低的箱子
            for(Cage entity:upCageList) {

                double cageX = 0;
                //判断选中的箱子左边能延伸多长
                Cage leftCage = leftExtend(car, entity);
                //如果为空，则左边没有阻挡的箱子，直接延伸到车厢最左边
                if (leftCage == null) {
                    cageX = entity.getEndPoint().getX();
                } else {
                    cageX = entity.getEndPoint().getX() - leftCage.getEndPoint().getX();
                }
                //判断选中的箱子右边能延伸多长
                Cage rightCage = rightExtend(car, entity);
                if (rightCage == null) {
                    cageX += car.getWidth() - entity.getEndPoint().getX();
                } else {
                    cageX += rightCage.getStartPoint().getX() - entity.getEndPoint().getX();
                }
                //判断此空间是否能容纳该箱子
                if (cageX >= cage.getWidth()) {
                    if (leftCage == null) {
                        cage.getStartPoint().setX(0);
                    } else {
                        cage.getStartPoint().setX(leftCage.getEndPoint().getX());
                    }
                    cage.getStartPoint().setY(entity.getEndPoint().getY());
                    cage.getEndPoint().setX(cage.getStartPoint().getX() + cage.getWidth());
                    cage.getEndPoint().setY(entity.getEndPoint().getY() + cage.getHeight());
                    if(cage.getEndPoint().getY()>car.getHeight()){
                        return false;
                    }
                    car.getCageList().add(cage);
                    break;
                }
            }
        }
        return true;
    }

    //判断是否有右边相邻的箱子
    public Boolean hasRightCage(Car car,Cage cage){
        for(Cage entity:car.getCageList()){
            if(entity.getStartPoint().getX()==cage.getEndPoint().getX()){
                if(!(entity.getStartPoint().getY()>=cage.getEndPoint().getY()
                  ||entity.getEndPoint().getY()<=cage.getStartPoint().getY())){
                    return true;
                }
            }
        }
        return false;
    }

    //判断上方是否有箱子
    public Cage hasUpCage(Car car,Cage cage){
        for(Cage entity:car.getCageList()){
            if(cage.getEndPoint().getY()==entity.getStartPoint().getY()
                    &&cage.getStartPoint().getX()<entity.getEndPoint().getX()
                &&cage.getEndPoint().getX()>entity.getEndPoint().getX()){
                return entity;
            }
        }
        return null;
    }

    //指定特定的箱子，查看其左边延伸时，允许能延伸的长度（左边的箱子高度低于目标箱子且上面没有叠放箱子）
    public Cage leftExtend(Car car,Cage cage){
        //找出所有高于该箱子高度的箱子
        Cage goal=null;
        for(Cage entity:car.getCageList()){
            if(entity.getEndPoint().getY()>cage.getEndPoint().getY()&&entity.getEndPoint().getX()<cage.getEndPoint().getX()){
                if(goal==null){
                    goal=entity;
                }else{
                    if(goal.getEndPoint().getX()<entity.getEndPoint().getX()){
                        goal=entity;
                    }
                }
            }
        }
        return goal;
    }

    //指定特定的箱子，查看其右边延伸时，允许能延伸的长度（左边的箱子高度低于目标箱子且上面没有叠放箱子）
    public Cage rightExtend(Car car,Cage cage){
        //找出所有高于该箱子高度的箱子
        Cage goal=null;
        for(Cage entity:car.getCageList()){
            if(entity.getEndPoint().getY()>cage.getEndPoint().getY()&&entity.getEndPoint().getX()>cage.getEndPoint().getX()){
                if(goal==null){
                    goal=entity;
                }else{
                    if(goal.getEndPoint().getX()>entity.getEndPoint().getX()){
                        goal=entity;
                    }
                }
            }
        }
        return goal;
    }
    public void chooseLoacation(){

    }
}
