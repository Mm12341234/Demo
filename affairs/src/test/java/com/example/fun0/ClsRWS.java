package com.example.fun0;

import java.util.List;

//轮盘赌选择
public class ClsRWS {
    ClsInit init = new ClsInit();
    ClsFitness fitness = new ClsFitness();

    /* fit[]适应度数组
     * group[]群体数组
     * GENE基因数
     * */
    public String[] RWS(String group[], int GENE, int temp, int arrivalNum, List<DropPoint> dropPointList){
        double p[] = new double[group.length];	//染色体概率数组
        String[] newgroup = new String[group.length];
        double F = 0;	//适应度的和

        double[] fit = fitness.fitAll(group,GENE,temp,arrivalNum,dropPointList);	//计算适应度数组

        //求适应度的和F
        for(int i = 0; i < fit.length; i++){
            F = F +fit[i];
        }

        //初始化p[]
        for(int i = 0; i < fit.length; i++){
            p[i] = fit[i] / F;
        }

        //求出适应度最大的个体maxStr,它的序号是max
        int max = fitness.mFitNum(fit);
        String maxStr = group[max];
        ClsDecode decode = new ClsDecode();
        int all;

        //转动轮盘，适应度大的被选中的概率大
        for (int i = 0; i < fit.length; i++){
            double r = Math.random();
            double q= 0;	//累计概率

            //适应度最大的个体直接继承
            if(i == max){
                newgroup[i] = maxStr;
                p[i] = 0;	//将其概率置空
                //System.out.println("继承的最大适应度为"+fit[i]);
                continue;
            }

            //遍历轮盘，寻找轮盘指针指在哪个区域
            for(int j = 0; j < fit.length; j++){
                q += p[j];
                if(r <= q){
                    newgroup[i] = group[j];	//如果被选中，保留进下一代
                    p[j] = 0;	//将其概率置空
                    break;
                }
                do{
                    String str=init.initSingle(GENE-GENE/temp);	//如果没被选中，被外来者取代
                    all=0;
                    List<Integer> list =decode.decode(str,GENE,temp,arrivalNum);
                    for(Integer entity:list){
                        all+=entity;
                    }
                    if(all<=arrivalNum){
                        newgroup[i]=str;
                    }
                }while(all>arrivalNum);
            }
        }
        return newgroup;
    }
}
