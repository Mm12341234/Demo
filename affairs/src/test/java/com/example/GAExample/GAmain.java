package com.example.GAExample;

public class GAmain {
    public static final int groupsize = 10;	//染色体数（群体中个体数）
    public static final double MP = 0.15;	//变异概率
    public static final double CP = 0.6;	//交叉概率
    public static final int ITERA = 1000;	//迭代次数
    public static final int accuracy = 8;	//精确度，选择精确到小数点后几位


    //求出精度对应的所需基因数
    int temp = (int) ((int)Math.log(6)+ accuracy*Math.log(10) );
    int GENE = temp * 2;	//基因数


    //输出原群体和适应度，测试用
    public void outAll(String[] group){
        ClsFitness fitness = new ClsFitness();
        System.out.println("原群体");
        for(String str:group){
            System.out.println(str);
        }

        double fit[] = fitness.fitAll(group,GENE);
        System.out.println("原群体适应度");
        for(double str:fit){
            System.out.println(str);
        }
    }

    //输出适应度最大值,以及返回最优的个体,测试用
    public int outMax(String str,String[] group){
        ClsFitness fitness = new ClsFitness();
        double[] fit = fitness.fitAll(group,GENE);
        double max = fitness.mFitVal(fit);
        System.out.println(str+"后适应度最大值为"+max);
        return fitness.mFitNum(fit);
    }

    public static void main(String[] args) {
        ClsInit init = new ClsInit();
        ClsFitness fitness = new ClsFitness();
        ClsRWS rws = new ClsRWS();
        ClsCross cross = new ClsCross();
        ClsMutation mutation = new ClsMutation();
        ClsDecode decode = new ClsDecode();
        GAmain ga = new GAmain();

        String group[] = init.initAll(ga.GENE,groupsize);	//初始化
        ga.outAll(group);

        for(int i = 0; i < ITERA; i++){
            group = rws.RWS(group, ga.GENE); //选择
            ga.outMax("选择",group);

            group = cross.cross(group,ga.GENE,CP);	//交叉
            ga.outMax("交叉",group);

            group = mutation.mutation(group, ga.GENE, MP);	//变异
            ga.outMax("变异",group);

            System.out.println("");
        }
        int max = ga.outMax("完成", group);
        double best[] = decode.decode(group[max], ga.GENE);	//解码
        double result = 3-fitness.fitSingle(group[max], ga.GENE);
        System.out.println("x1 = "+best[0]+"\n"+"x2 = "+best[1]);
        System.out.println("最小值为"+result);
    }
}
