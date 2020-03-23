package com.example.GAExample;

//解码
public class ClsDecode {
    //单个染色体解码
    public double[] decode(String single,int GENE){
        //二进制数前GENE/2位为x的二进制字符串，后GENE/2位为y的二进制字符串
        int a=Integer.parseInt(single.substring(0,GENE/2),2);
        int b=Integer.parseInt(single.substring(GENE/2,GENE),2);
        double[] x = {-1,-1};//new double[2];
//        System.out.println(Math.pow(2, GENE/2) - 1);
        x[0] = a * (6.0 - 0) / (Math.pow(2, GENE/2) - 1);	//x的基因
        x[1] = b * (6.0 - 0) / (Math.pow(2, GENE/2) - 1);	//y的基因
        return x;
    }
}