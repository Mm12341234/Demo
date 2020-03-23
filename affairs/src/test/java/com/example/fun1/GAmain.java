package com.example.fun1;

import java.util.ArrayList;
import java.util.List;

public class GAmain {

    public static final int groupsize = 100;	//染色体数（群体中个体数）
    public static final double MP = 0.15;	//变异概率
    public static final double CP = 0.6;	//交叉概率
    public static final int ITERA = 500;	//迭代次数

    //求出精度对应的所需基因数
    public int temp;
    public int GENE;	//基因数

    public List<DropPoint> dropPointList=new ArrayList<>();
    public Integer arrivalNum;

    public static void main(String[] args){
        GAmain ga=new GAmain();
        ga.initData();
        ga.initBit();
        ClsInit init = new ClsInit();
        ClsFitness fitness = new ClsFitness();
        ClsRWS rws = new ClsRWS();
        ClsCross cross = new ClsCross();
        ClsMutation mutation = new ClsMutation();
        ClsDecode decode = new ClsDecode();

        String group[] = init.initAll(ga.GENE,groupsize,ga.temp,ga.dropPointList.size(),ga.arrivalNum);	//初始化

        for(int i = 0; i < ITERA; i++){
            group = rws.RWS(group, ga.GENE,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList); //选择
            ga.outMax("选择",group,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);

            group = cross.cross(group,ga.GENE,CP,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);	//交叉
            ga.outMax("交叉",group,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);

            group = mutation.mutation(group, ga.GENE, MP,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);	//变异
            ga.outMax("变异",group,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);

        }
        int max = ga.outMax("完成", group,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);
        List<Integer> list= decode.decode(group[max], ga.GENE,ga.dropPointList.size(),ga.arrivalNum);	//解码
        double result = 3-fitness.fitSingle(group[max], ga.GENE,ga.dropPointList.size(),ga.arrivalNum,ga.dropPointList);
        System.out.println("x1 = "+list.get(0)+"\n"
                +"x2 = "+list.get(1)+"\n"
                +"x3 = "+list.get(2)+"\n"
                +"x4 = "+list.get(3)+"\n"
                +"x5 = "+(ga.arrivalNum-list.get(0)-list.get(1)-list.get(2)-list.get(3)));
    }

    public void initData(){
        DropPoint dropPoint=new DropPoint();
        dropPoint.setName("d1");
        dropPoint.setNum(500);
        dropPoint.setTime("202002230900");
        DropPoint dropPoint1=new DropPoint();
        dropPoint1.setName("d2");
        dropPoint1.setNum(1000);
        dropPoint1.setTime("202002230700");
        DropPoint dropPoint2=new DropPoint();
        dropPoint2.setName("d3");
        dropPoint2.setNum(300);
        dropPoint2.setTime("202002231300");
        DropPoint dropPoint3=new DropPoint();
        dropPoint3.setName("d4");
        dropPoint3.setNum(600);
        dropPoint3.setTime("202002230600");
        DropPoint dropPoint4=new DropPoint();
        dropPoint4.setName("d5");
        dropPoint4.setNum(600);
        dropPoint4.setTime("202002230710");
        dropPointList.add(dropPoint);
        dropPointList.add(dropPoint1);
        dropPointList.add(dropPoint2);
        dropPointList.add(dropPoint3);
        dropPointList.add(dropPoint4);
        arrivalNum=224;
    }

    //确定基因组的位数
    public void initBit(){
        for(int i=0;i<20;i++){
            if(arrivalNum<=Math.pow(2,i)){
                temp=i;
                break;
            }
        }
        //确定基因的长度
        GENE=temp*dropPointList.size();
    }

    //输出原群体和适应度，测试用
    public void outAll(String[] group,int temp,int arrivalNum,List<DropPoint> dropPointList){
        ClsFitness fitness = new ClsFitness();
        System.out.println("原群体");
        for(String str:group){
            System.out.println(str);
        }

        double fit[] = fitness.fitAll(group,GENE,temp,arrivalNum, dropPointList);
        System.out.println("原群体适应度");
        for(double str:fit){
            System.out.println(str);
        }
    }

    //输出适应度最大值,以及返回最优的个体,测试用
    public int outMax(String str,String[] group,int temp,int arrivalNum , List<DropPoint> dropPointList){
        ClsFitness fitness = new ClsFitness();
        double[] fit = fitness.fitAll(group,GENE,temp,arrivalNum,dropPointList);
        double max = fitness.mFitVal(fit);
        System.out.println(str+"后适应度最大值为"+max);
        return fitness.mFitNum(fit);
    }



}
