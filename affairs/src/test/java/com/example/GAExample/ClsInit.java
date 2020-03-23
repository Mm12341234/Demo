package com.example.GAExample;

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
    public String[] initAll(int GENE,int groupsize){
        String[] iAll = new String[groupsize];
        for(int i = 0; i < groupsize; i++){
            iAll[i] = initSingle(GENE);
        }
        return iAll;
    }
}