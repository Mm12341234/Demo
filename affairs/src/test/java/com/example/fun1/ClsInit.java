package com.example.fun1;

import java.util.List;

//初始化群体（编码）
public class ClsInit {

    //初始化一条染色体
    public String initSingle(int GENE){
        String res = "";
        for(int i = 0; i < GENE; i++){
            if(Math.random() < 0.5){
                     res += 0;
            }else{
                res +=1;
            }
        }
        return res;
    }

    //初始化一组染色体
    public String[] initAll(int GENE,int groupsize,int temp,int dropNum,int arrivalNum){
        ClsDecode clsDecode=new ClsDecode();
        String[] iAll = new String[groupsize];
        List<Integer> list;
        Integer num;
        for(int i = 0; i < groupsize; i++){
            String str = initSingle(GENE-temp);
            list =clsDecode.decode(str,GENE,dropNum,arrivalNum);
            num=0;
            for(Integer entity:list){
                num+=entity;
            }
            if(num>arrivalNum){
                i--;
            }else{
                iAll[i] = str;
            }
        }
        return iAll;
    }
}