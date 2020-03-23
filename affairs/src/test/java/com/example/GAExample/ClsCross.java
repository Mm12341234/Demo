package com.example.GAExample;

//交叉
public class ClsCross {
    ClsFitness fitness = new ClsFitness();
    //group群体
    //GENE基因数
    //mFitNum最大适应度染色体序号
    public String[] cross(String[] group,int GENE,double crossRate){
        String temp1, temp2;
        int pos = 0;

        double[] fit = fitness.fitAll(group,GENE);
        int mFitNum = fitness.mFitNum(fit);	//计算适应度最大的染色体序号
        String max = group[mFitNum];

        for(int i = 0; i < group.length; i++){
            if(Math.random() < crossRate){
                pos = (int)(Math.random()*GENE) + 1;	//交叉点
                temp1 = group[i].substring(0, pos) + group[(i+1) % group.length].substring(pos);	//%用来防止数组越界
                temp2 = group[(i+1) % group.length].substring(0, pos) + group[i].substring(pos);
                group[i] = temp1;
                group[(i+1) % group.length] = temp2;
            }
        }
        group[0] = max;
        return group;
    }
}