package com.example;

import com.sun.tools.javah.Gen;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Liumq
 * @Date 2020/02/17
 * @describe 只是模拟一种零件的多落点拆分
 */
public class MultiDropPointSplitProblem {

    int summonsNum;  //传票包装数
    int minPackingNum; //最小包装数
    Random ra = new Random();
    double[] probabilityArr;// 种群中各个个体的累计概率
    int geneNum;// 染色体长度
    double crossRate, mutationRate;// 交叉概率和变异概率
    int populationScale;// 种群规模
    int T;// 进化代数
    int t;// 当前代数
    Gene[] bestGhArr;   // 所有代数中最好的染色体
    Gene[][] oldMatrix;// 初始种群，父代种群，行数表示种群规模，一行代表一个个体，即染色体，列表示染色体基因片段
    Gene[][] newMatrix;// 新的种群，子代种群
    double[] fitnessArr;// 种群适应度，表示种群中各个个体的适应度
    double bestFitness;// 所有代数中最好的染色体的适应度
    List<Gene> produceDemandList;

    List<DropPoint> dropPointList;//所有的落点
    List<SupplyNum> arrivalNumList;//到货的数量

    // 初始化函数
    void initData() {
        populationScale = 100;// 种群规模
        crossRate = 0.9;// 交叉概率
        mutationRate = 0.09;// 变异概率，实际为(1-Pc)*0.9=0.09
        T = 3000;// 进化代数
        geneNum=100; //染色体长度
        summonsNum=300; //最佳传票数
        minPackingNum=150; //最小包装数
        bestFitness = 0;// 所有代数中最好的染色体的适应度
        oldMatrix = new Gene[populationScale][geneNum];// 初始种群，父代种群，行数表示种群规模，一行代表一个个体，即染色体，列表示染色体基因片段
        newMatrix = new Gene[populationScale][geneNum];// 新的种群，子代种群
        fitnessArr = new double[populationScale];// 种群适应度，表示种群中各个个体的适应度
        probabilityArr = new double[populationScale];// 种群中各个个体的累计概率
        bestGhArr = new Gene[geneNum];// 所有代数中最好的染色体

        dropPointList=new ArrayList<>();
        dropPointList.add(new DropPoint("d1",74));
        dropPointList.add(new DropPoint("d2",0));
        dropPointList.add(new DropPoint("d3",100));
        SupplyNum supplyNumOne=new SupplyNum();
        arrivalNumList=new ArrayList<>();
        supplyNumOne.setTime("202002161000");
        supplyNumOne.setNum(2900);
        arrivalNumList.add(supplyNumOne);
        SupplyNum supplyNumTwo=new SupplyNum();
        supplyNumTwo.setTime("202002191200");
        supplyNumTwo.setNum(3000);
        arrivalNumList.add(supplyNumTwo);

        produceDemandList=new ArrayList<>();
        produceDemandList.add(new Gene("d1","202002171000",450));
        produceDemandList.add(new Gene("d1","202002171400",550));
        produceDemandList.add(new Gene("d1","202002181100",350));
        produceDemandList.add(new Gene("d1","202002192000",50));
        produceDemandList.add(new Gene("d1","202002201100",350));

        produceDemandList.add(new Gene("d2","202002171000",450));
        produceDemandList.add(new Gene("d2","202002181100",350));
        produceDemandList.add(new Gene("d2","202002191600",50));
        produceDemandList.add(new Gene("d2","202002192000",50));

        produceDemandList.add(new Gene("d3","202002171000",20));
        produceDemandList.add(new Gene("d3","202002171500",50));
        produceDemandList.add(new Gene("d3","202002181100",350));
        produceDemandList.add(new Gene("d3","202002192000",50));
    }

    // 初始化种群
    void initGroup() {

        // 种群数
        for (int x = 0; x < populationScale; x++) {

            for(int y=0;y<produceDemandList.size();y++){
                int ran1 = ra.nextInt(summonsNum);
                int ran2=ra.nextInt(10);
                int timeRan1=ra.nextInt(5)*60;
                if(ran2<5){
                    ran1=-1*ran1;
                    timeRan1=-1*timeRan1;
                }
                Gene gene=new Gene(produceDemandList.get(y).getDropPoint(),
                        DateUtils.minuteSub(produceDemandList.get(y).getTime(),timeRan1),
                        Math.abs(produceDemandList.get(y).getNum()+ran1));
                oldMatrix[x][y]=gene;
            }
        }
    }

    // 挑选某代种群中适应度最高的个体，直接复制到子代中，前提是已经计算出各个个体的适应度Fitness[max]
    void selectBestChrosome() {
        int k, maxid;
        double maxevaluation;
        maxid = 0;
        maxevaluation = fitnessArr[0];
        for (k = 1; k < populationScale; k++) {
            if (maxevaluation < fitnessArr[k]) {
                maxevaluation = fitnessArr[k];
                maxid = k;
            }
        }

        if (bestFitness < maxevaluation) {
            bestFitness = maxevaluation;
            System.arraycopy(oldMatrix[maxid], 0, bestGhArr, 0, geneNum);
        }
        // 复制染色体，k表示新染色体在种群中的位置，kk表示旧的染色体在种群中的位置
        copyChrosome(0, maxid);// 将当代种群中适应度最高的染色体k复制到新种群中，排在第一位0
    }

    // 计算种群中各个个体的累积概率，前提是已经计算出各个个体的适应度Fitness[max]，作为赌轮选择策略一部分，Pi[max]
    void countRate() {
        int k;
        double sumFitness = 0;// 适应度总和

        for (k = 0; k < populationScale; k++) {
            sumFitness += fitnessArr[k];
        }

        // 计算各个个体累计概率
        probabilityArr[0] = fitnessArr[0] / sumFitness;
        for (k = 1; k < populationScale; k++) {
            probabilityArr[k] = fitnessArr[k] / sumFitness + probabilityArr[k - 1];
        }
    }


    // 产生随机数
    int select() {
        int k;
        double ran1;
        ran1 = Math.abs(ra.nextDouble());
        for (k = 0; k < populationScale; k++) {
            if (ran1 <= probabilityArr[k]) {
                break;
            }
        }
        return k;
    }

    // 类OX交叉算子,交叉算子不够优秀
    void oxCrossover(int k1, int k2) {
        int ran1, ran2, temp;
        Gene[] Gh1 = new Gene[geneNum];
        Gene[] Gh2 = new Gene[geneNum];
        int index=0;
        for(int i=0;i<newMatrix[0].length;i++){
            if(newMatrix[0][i]!=null){
                index++;
            }else{
                break;
            }
        }
        ran1 = ra.nextInt(index-1);
        ran2 = ra.nextInt(index-1);
        while (ran1 == ran2)
            ran2 = ra.nextInt(index-1);

        // 确保ran1<ran2
        if (ran1 > ran2) {
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }

        for(int i=0;i<newMatrix[0].length;i++){
            if(i>=ran1&&i<=ran2){
                Gh1[i] = newMatrix[k2][i];
                Gh2[i] = newMatrix[k1][i];
            }else{
                Gh1[i] = newMatrix[k1][i];
                Gh2[i] = newMatrix[k2][i];
            }
        }

        System.arraycopy(Gh1, 0, newMatrix[k1], 0, geneNum);
        System.arraycopy(Gh2, 0, newMatrix[k2], 0, geneNum);

    }

    public void swap(Gene arr[], int index1, int index2) {

        String time = arr[index1].getTime();
        Integer num=arr[index1].getNum();
        arr[index1].setTime(arr[index2].getTime());
        arr[index1].setNum(arr[index2].getNum());
        arr[index2].setTime( time);
        arr[index2].setNum( num);
    }

    // 对种群中的第k个染色体进行变异
    void mutation(int k) {
        int ran1, ran2;
        int index=0;
        for(int i=0;i<newMatrix[0].length;i++){
            if(newMatrix[0][i]!=null){
                index++;
            }else{
                break;
            }
        }
        //数量上的突变
        ran1 = ra.nextInt(summonsNum)*ra.nextInt(10);
        ran2=ra.nextInt(index-1);
        newMatrix[k][ran2].setNum(ran1);
        //时间上的突变
        String time = DateUtils.minuteSub(newMatrix[k][ran2].getTime(),ra.nextInt(60)*ra.nextInt(60));
        newMatrix[k][ran2].setTime(time);

    }

    // 复制染色体，k表示新染色体在种群中的位置，kk表示旧的染色体在种群中的位置
    void copyChrosome(int k, int kk) {
        System.arraycopy(oldMatrix[kk], 0, newMatrix[k], 0, geneNum);
    }

    // 进化函数，保留最优
    void evolution() {
        int k, selectId;
        double r;// 大于0小于1的随机数
        // 挑选某代种群中适应度最高的个体
        selectBestChrosome();
        // 赌轮选择策略挑选scale-1个下一代个体
        for (k = 1; k < populationScale; k++) {
            selectId = select();
            copyChrosome(k, selectId);
        }
        for (k = 0; k + 1 < populationScale / 2; k = k + 2) {
            r = Math.abs(ra.nextDouble());
            // crossover
            if (r < crossRate) {
                oxCrossover(k, k + 1);// 进行交叉
            } else {
                r = Math.abs(ra.nextDouble());
                if (r < mutationRate) {
                    mutation(k);
                }
                r = Math.abs(ra.nextDouble());
                if (r < mutationRate) {
                    mutation(k + 1);
                }
            }
        }

        // 剩最后一个染色体没有交叉L-1
        if (k == populationScale / 2 - 1) {
            r = Math.abs(ra.nextDouble());
            if (r < mutationRate) {
                mutation(k);
            }
        }
        System.arraycopy(bestGhArr, 0,newMatrix[0] , 0, geneNum);
    }

    // 染色体评价函数，输入一个染色体，得到该染色体评价值
    double caculateFitness(Gene[] gene) {
        //最优度
        double great=calcuSatisProduce(gene);

        //找出这个染色体的某个落点
        List<List<Gene>> _demandDropPointList=new ArrayList<>();
        List<List<Gene>> demandDropPointList=new ArrayList<>();
        List<Gene> dropPoint;
        Set<String> dropPointSet=new HashSet<>();
        for(Gene entity:produceDemandList){
            dropPointSet.add(entity.getDropPoint());
        }
        for(String dropName:dropPointSet){
            dropPoint=new ArrayList<>();
            for(Gene entity:produceDemandList){
                if(dropName.equals(entity.getDropPoint())){
                    dropPoint.add(entity);
                }
            }
            _demandDropPointList.add(dropPoint);
        }
        //对每个落点的需求按时间排序
        for(List<Gene> entity:_demandDropPointList){
            //第一步，根据配送线路,落点，供件时间，顺序排序
            Comparator<Gene> byTime = Comparator.comparing(Gene::getTime);
            //排序后得到的summonsList序列
            entity= entity.stream().sorted(byTime).collect(Collectors.toList());
            demandDropPointList.add(entity);
        }

        //落点剩余数量复制
        List<DropPoint> _dropPointList=new ArrayList<>();
        for(DropPoint en:dropPointList){
            DropPoint object=new DropPoint(en.getName(),en.getSurplusNum());
            _dropPointList.add(object);
        }

        //计算每个落点下次供件的
        for(List<Gene> entity:demandDropPointList){
            List<Gene> geneList=new ArrayList<>();
            //该染色关于这个落点基因的排序
            for(int i=0;i<gene.length;i++){
                if(gene[i]!=null&&entity.get(0).getDropPoint().equals(gene[i].getDropPoint())){
                    geneList.add(gene[i]);
                }
            }
            Comparator<Gene> byTime = Comparator.comparing(Gene::getTime);
            geneList=geneList.stream().sorted(byTime).collect(Collectors.toList());

            //第一步，检查数量以及时间，确定惩罚的权重
            int i=0;
            for(Gene demand:entity){
                //最好的供件时间
                String bestSupplyTime;
                Integer bestNum;
                //找出最好的供件时间点
                Integer suplusNum=0;
                for(DropPoint en:_dropPointList){
                    //计算剩余的量能应付多长时间
                    if(demand.getDropPoint().equals(en.getName())){
                        suplusNum=en.getSurplusNum();
                        break;
                    }
                }
                bestSupplyTime=DateUtils.minuteSub(demand.getTime(),-suplusNum);
                //计算出最少的供件数量
                bestNum=demand.getNum()-suplusNum;
                if(bestNum<=0){
                    //进行下个节点的比较
                    for(DropPoint en:_dropPointList){
                        //计算剩余的量能应付多长时间
                        if(demand.getDropPoint().equals(en.getName())){
                            en.setSurplusNum(-bestNum);
                            i++;
                            break;
                        }
                    }
                    continue;
                }
                //如果需求量不满足
                if(bestNum>geneList.get(i).getNum()){
                    great=great-(bestNum-geneList.get(i).getNum());
                }else{
                    if(geneList.get(i).getNum()%summonsNum==0){
                        great=great+500;
                    }else if(geneList.get(i).getNum()%minPackingNum==0){
                        great=great+400;
                    }else{
                        great=great+200-(geneList.get(i).getNum()-demand.getNum());
                    }

                }

                //查看供应的时间点
                if(DateUtils.timeDifference(bestSupplyTime,geneList.get(i).getTime())>0){
                    great=great-DateUtils.timeDifference(bestSupplyTime,geneList.get(i).getTime());
                }else{
                    double timeDistance=DateUtils.timeDifference(geneList.get(i).getTime(),bestSupplyTime);
                    if(timeDistance==0){
                        timeDistance=1;
                    }
                    double timePunish=400*(1/timeDistance);
                    great=great-timePunish;
                    BigDecimal d = new BigDecimal(great);
                    great = d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }

                //进行下个节点的比较
                for(DropPoint en:_dropPointList){
                    //计算剩余的量能应付多长时间
                    if(demand.getDropPoint().equals(en.getName())){
                        if(geneList.get(i).getNum()-bestNum<0){
                            en.setSurplusNum(0);
                        }else{
                            en.setSurplusNum(geneList.get(i).getNum()-bestNum);
                        }
                        break;
                    }
                }
                i++;
            }
        }

        return Double.valueOf(String.format("%.2f", great));
    }

    /**
     * 时间的评估
     */


    /**
     * 数量的评估
     */


    /**
     * 计算是否满足生产
     */
    Integer calcuSatisProduce(Gene[] gene){
        int great=0;
        List<SupplyNum> list=new ArrayList<>();
        for(int i=0;i<gene.length;i++){
            if(gene[i]!=null){
                SupplyNum object=new SupplyNum();
                object.setType(0);
                object.setNum(gene[i].getNum());
                object.setTime(gene[i].getTime());
                list.add(object);
            }
        }
        for(SupplyNum entity:arrivalNumList){
            entity.setType(1);
            list.add(entity);
        }
        //第一步，根据配送线路,落点，供件时间，顺序排序
        Comparator<SupplyNum> byTime = Comparator.comparing(SupplyNum::getTime);
        list= list.stream().sorted(byTime).collect(Collectors.toList());

        //计算是否满足生产
        Integer num=0;
        Integer arrivalNum=0;
        for(SupplyNum supplyNum:list){
            if(supplyNum.getType()==1){
                if(num==0){
                    arrivalNum=supplyNum.getNum();
                }else{
                    if(arrivalNum>num){
                        great+=2000;
                    }else{
                        great+=2000-(num-arrivalNum);
                    }
                    arrivalNum=supplyNum.getNum();
                }
                num=0;
            }else{
                num+=supplyNum.getNum();
            }
        }
        if(arrivalNum>num){
            great+=2000;
        }else{
            great+=2000-(num-arrivalNum);
        }
        return great;
    }

    void solveMultiSplit(){
        // 初始化数据，不同问题初始化数据不一样
        initData();

        // 初始化种群
        initGroup();
        Gene[] tempGA = new Gene[geneNum];

        // 计算初始化种群适应度，Fitness[max]
        for (int k = 0; k < populationScale; k++) {
            //复制每一条染色体，也就是一个解
            for (int i = 0; i < geneNum; i++) {
                tempGA[i] = oldMatrix[k][i];
            }
            //计算其合适度
            fitnessArr[k] = caculateFitness(tempGA);
        }
        countRate();
        for (t = 0; t < T; t++) {
            evolution();// 进化函数，保留最优
            // 将新种群newMatrix复制到旧种群oldMatrix中，准备下一代进化
            for (int k = 0; k < populationScale; k++) {
                System.arraycopy(newMatrix[k], 0, oldMatrix[k], 0, geneNum);
            }
            // 计算种群适应度，Fitness[max]
            for (int k = 0; k < populationScale; k++) {
                System.arraycopy(oldMatrix[k], 0, tempGA, 0, geneNum);
                fitnessArr[k] = caculateFitness(tempGA);
            }
            // 计算种群中各个个体的累积概率，Pi[max]
            countRate();
            for(int i=0;i<13;i++){
                System.out.println(bestGhArr[i].dropPoint+"  "+bestGhArr[i].time+"  "+bestGhArr[i].num);
            }
            System.out.println("---------------------------------------");
        }
    }

    public static void main(String[] args){
        MultiDropPointSplitProblem entity=new MultiDropPointSplitProblem();
        entity.solveMultiSplit();
    }
}

class Gene{
    public String dropPoint;
    public String time;
    public Integer num;

    public Gene(String dropPoint,String time,Integer num){
        this.dropPoint=dropPoint;
        this.time=time;
        this.num=num;
    }

    public String getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(String dropPoint) {
        this.dropPoint = dropPoint;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}

class SupplyNum{
    public String time;
    public Integer num;
    public Integer type;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

class DropPoint{
    public String name;
    public Integer surplusNum;
    public Integer surplusTimeNum;

    public DropPoint(String name,Integer surplusNum){
        this.name=name;
        this.surplusNum=surplusNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }

    public Integer getSurplusTimeNum() {
        return surplusTimeNum;
    }

    public void setSurplusTimeNum(Integer surplusTimeNum) {
        this.surplusTimeNum = surplusTimeNum;
    }
}

class DateUtils{
    /**
     * 比较两个日期相差几分钟
     */
    public static Integer timeDifference(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date s = null;
        Date et = null;
        try {
            s = sdf.parse(start);
            et = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long diff = et.getTime() - s.getTime();
        Integer num = Math.toIntExact(diff / 1000 / 60);
        //求余数，剔除中间休息的天数
        num = num % 1440;
        return num;
    }

    /**
     * 日期减去多少分钟
     */
    public static String minuteSub(String date, Integer num) {
        //根据date的长度以及样式，进行转换
        // date是 yyyyMMddHHmm 类型
        SimpleDateFormat normal = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat sdf = null;
        Integer length = date.length();
        if (length == 12) {
            sdf = new SimpleDateFormat("yyyyMMddHHmm");
        } else if (length == 16) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        //date是yy-MM-dd HH:mm类型
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MINUTE, -num);
        Date startItem = calendar.getTime();
        Long time = Long.parseLong(normal.format(startItem));
        return time.toString();
    }
}

