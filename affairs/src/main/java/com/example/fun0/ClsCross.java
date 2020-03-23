package com.example.fun0;

import java.util.List;

//交叉
public class ClsCross {
    ClsFitness fitness = new ClsFitness();
    //group群体
    //GENE基因数
    //mFitNum最大适应度染色体序号
    public String[] cross(String[] group, int GENE, double crossRate, int temp, int arrivalNum, List<DropPoint> dropPointList){
        String temp1, temp2;
        int pos = 0;

        double[] fit = fitness.fitAll(group,GENE,temp,arrivalNum,dropPointList);	//计算适应度数组
        int mFitNum = fitness.mFitNum(fit);	//计算适应度最大的染色体序号
        String max = group[mFitNum];

        ClsDecode clsDecode=new ClsDecode();
        List<Integer> list,list1;
        Integer all,all1;
        for(int i = 0; i < group.length; i++){
            if(Math.random() < crossRate){
                pos = (int)(Math.random()*(GENE-GENE/temp)) + 1;	//交叉点
                temp1 = group[i].substring(0, pos) + group[(i+1) % group.length].substring(pos);	//%用来防止数组越界
                temp2 = group[(i+1) % group.length].substring(0, pos) + group[i].substring(pos);
                list =clsDecode.decode(temp1,GENE,dropPointList.size(),arrivalNum);
                list1 =clsDecode.decode(temp2,GENE,dropPointList.size(),arrivalNum);
                all=0;
                all1=0;
                for(Integer entity:list){
                    all+=entity;
                }
                for(Integer entity:list1){
                    all1+=entity;
                }
                if(all>arrivalNum||all1>arrivalNum){
                    i--;
                }else{
                    group[i] = temp1;
                    group[(i+1) % group.length] = temp2;
                }

            }
        }
        group[0] = max;
        return group;
    }
}