package com.example.fun0;


import java.util.List;

public class ClsFitness {
    //计算个体的适应度
    public double fitSingle(String str,int GENE,int temp,int arrivalNum, List<DropPoint> dropPointList){
//        System.out.println(str);
        ClsDecode decode = new ClsDecode();
        List<Integer> list =decode.decode(str,GENE,temp,arrivalNum);
        int all=0;
        //计算剩余的数量
        for(Integer entity:list){
            all+=entity;
        }
        list.add(arrivalNum-all);
        //适应度计算公式
        //问题：如果计算出来有负有正该怎么处理？
        int i=0;
        double minTime=100000000;
        for(Integer entity:list){
//            System.out.print(entity+"    ");
            String strn=DateUtils.minuteSub(dropPointList.get(i).getTime(),-entity);
            Integer num=Integer.parseInt(strn.substring(4));
            if(num<minTime){
                minTime=num;
            }
            i++;
        }
//        System.out.println();
        return minTime;
    }

    //批量计算数组的适应度
    public double[] fitAll(String str[],int GENE,int temp,int arrivalNum, List<DropPoint> dropPointList){
        double [] fit = new double[str.length];
        for(int i = 0;i < str.length; i++){
            fit[i] = fitSingle(str[i],GENE,temp,arrivalNum,dropPointList);
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
