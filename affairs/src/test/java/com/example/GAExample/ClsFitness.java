package com.example.GAExample;

//适应度
import java.lang.Math;
public class ClsFitness {
    //计算个体的适应度
    public double fitSingle(String str,int GENE){
        ClsDecode decode = new ClsDecode();
        double[] x =decode.decode(str,GENE);

        //适应度计算公式
        //问题：如果计算出来有负有正该怎么处理？
        double fitness = Math.sin(2 * x[0]) * Math.sin(2 * x[0])
                + Math.sin(2 * x[1]) * Math.sin(2 * x[1]);	//sin+sin越大，3-sin-sin越小，即得到的值越小个体的适应度就越大
        return fitness;
    }

    //批量计算数组的适应度
    public double[] fitAll(String str[],int GENE){
        double [] fit = new double[str.length];
        for(int i = 0;i < str.length; i++){
            fit[i] = fitSingle(str[i],GENE);
        }
        return fit;
    }

    //适应度最值（返回序号）
    public int mFitNum(double fit[]){
        double m = fit[0];
        int n = 0;
        for(int i = 0;i < fit.length; i++){
            if(fit[i] > m){
                //最大值
                m = fit[i];
                n = i;
            }
        }
        return n;
    }



    //适应度最值（返回适应度）
    public double mFitVal(double fit[]){
        double m = fit[0];
        for(int i = 0;i < fit.length; i++){
            if(fit[i] > m){
                //最大值
                m = fit[i];
            }
        }
        return m;
    }
}
