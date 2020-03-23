package com.example.fun0;



import com.example.entity.ArrivalEntity;
import com.example.entity.ChainspeedEntity;
import com.example.entity.ProducePlanEntity;
import java.util.*;
import java.util.stream.Collectors;

public class SolveSplit {

    private int summonsNum;  //传票包装数
    private int minPackingNum; //最小包装数
    private int wareHouseSurplus; //仓库剩余数
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

    List<DropPointSurplus> dropPointList;//所有的落点
    List<SupplyNum> arrivalNumList;//到货的数量
    List<ChainspeedEntity> chainspeedList;


    // 初始化函数
    void initData(List<ArrivalEntity> arrivalList, List<ProducePlanEntity> planEntityList, List<ChainspeedEntity> chainspeedEntityList) {
        populationScale = 100;// 种群规模
        crossRate = 0.9;// 交叉概率
        mutationRate = 0.09;// 变异概率，实际为(1-Pc)*0.9=0.09
        T = 3000;// 进化代数
        geneNum=100; //染色体长度
        wareHouseSurplus=0;
        bestFitness = 0;// 所有代数中最好的染色体的适应度
        oldMatrix = new Gene[populationScale][geneNum];// 初始种群，父代种群，行数表示种群规模，一行代表一个个体，即染色体，列表示染色体基因片段
        newMatrix = new Gene[populationScale][geneNum];// 新的种群，子代种群
        fitnessArr = new double[populationScale];// 种群适应度，表示种群中各个个体的适应度
        probabilityArr = new double[populationScale];// 种群中各个个体的累计概率
        bestGhArr = new Gene[geneNum];// 所有代数中最好的染色体

        //整理落点的信息
        produceDemandList=new ArrayList<>();
        dropPointList=new ArrayList<>();
        for(ProducePlanEntity entity:planEntityList){
            int flag=0;
            for(DropPointSurplus dropPointSurplus:dropPointList){
                if(entity.getDropPoint().equals(dropPointSurplus.getName())){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                dropPointList.add(new DropPointSurplus(entity.getDropPoint(),0));
                //整理最小包装数和传票数
                summonsNum=entity.getSummonNum();
                minPackingNum=entity.getMinpacking();
            }
            //整理需求关系
            produceDemandList.add(new Gene(entity.getDropPoint(),
                                           entity.getStartTime(),
                                           entity.getDemandNum(),
                                           entity.getNextCarTypeOptimal(),
                                           entity.getFarmId(),entity.getCoefficient()));
        }
        //整理到货关系
        arrivalNumList=new ArrayList<>();
        for(ArrivalEntity entity:arrivalList){
            SupplyNum object=new SupplyNum();
            object.setTime(entity.getReceivingDay());
            Integer num=Integer.parseInt(entity.getOrderQuantity())+Integer.parseInt(entity.getQuantityAdjusted());
            object.setNum(num);
            arrivalNumList.add(object);
        }
        //排序
        Comparator<SupplyNum> byTime = Comparator.comparing(SupplyNum::getTime);
        //排序后得到的summonsList序列
        arrivalNumList= arrivalNumList.stream().sorted(byTime).collect(Collectors.toList());
        //整理链速
        chainspeedList=chainspeedEntityList;
    }


    public void solve(List<ArrivalEntity> arrivalList, List<ProducePlanEntity> planEntityList, List<ChainspeedEntity> chainspeedEntityList){

        initData(arrivalList,planEntityList,chainspeedEntityList);
        //整理一下需求
        sortOutDemandList();
        List<DropPoint> summonsList=splitBySummonsNum();
        //如何将折中到完美光谱中选择
        List<DropPoint> list=lightSpectrum(summonsList);
        System.out.println("零件号："+arrivalList.get(0).getParts());
        System.out.println("传票数："+summonsNum+",最小包装数："+minPackingNum);
        int flag=0;
        int all=0;
        int everyUseNum=0;
        int arrivalNum=0;
        int demandNum=0;
        if(list==null){
            return;
        }
        for(DropPoint entity:list){
            if(flag<arrivalNumList.size()){
                for(int i=flag;i<arrivalNumList.size();i++){
                    if(DateUtils.timeDifference(arrivalNumList.get(i).getTime(),entity.getSupplyTime())>0){
                        System.out.println("总共到货数："+arrivalNum);
                        if(everyUseNum!=0){
                            System.out.println("使用了多少："+everyUseNum);
                            System.out.println("库存剩余数："+arrivalNum+"-"+everyUseNum+"="+(arrivalNum-everyUseNum));
                            arrivalNum-=everyUseNum;
                            everyUseNum=0;
                        }
                        arrivalNum+=arrivalNumList.get(i).getNum();
                        System.out.println("到货时间："
                                +arrivalNumList.get(i).getTime().substring(0,4)
                                +"-"+arrivalNumList.get(i).getTime().substring(4,6)
                                +"-"+arrivalNumList.get(i).getTime().substring(6,8)
                                +" "+arrivalNumList.get(i).getTime().substring(8,10)
                                +":"+arrivalNumList.get(i).getTime().substring(10)
                                +",到货数量："
                                +arrivalNumList.get(i).getNum());
                        flag++;
                    }
                }
            }
            System.out.println("落点："+entity.getName()
                    +", 需求时间："+entity.getTime().substring(0,4)
                    +"-"+entity.getTime().substring(4,6)
                    +"-"+entity.getTime().substring(6,8)
                    +" "+entity.getTime().substring(8,10)
                    +":"+entity.getTime().substring(10,12)
                    +", 需求量："+entity.getNum()
                    +", 线边结存："+entity.getSurplusNum()
                    +", 是否转车型："+entity.getChangeVehicle()
                    +", 供件时间："+entity.getSupplyTime().substring(0,4)
                    +"-"+entity.getSupplyTime().substring(4,6)
                    +"-"+entity.getSupplyTime().substring(6,8)
                    +" "+entity.getSupplyTime().substring(8,10)
                    +":"+entity.getSupplyTime().substring(10,12)
                    +", 供件数量："+entity.getSupplyNum());
            all+=entity.getSupplyNum();
            demandNum+=entity.getNum();
            everyUseNum+=entity.getSupplyNum();
        }
        System.out.println("使用了多少："+everyUseNum);
        System.out.println("库存剩余数："+arrivalNum+"-"+everyUseNum+"="+(arrivalNum-everyUseNum));
        System.out.println("需求总数："+demandNum);
        System.out.println("供件总数："+all);
    }

    public void sortOutDemandList(){

        List<Gene> list=new ArrayList<>();

        for(Gene entity:produceDemandList){
            String itemTime;
            if(entity.getNum()>summonsNum){
                do{
                    Gene gene=new Gene(entity.getDropPoint(),entity.getTime(),summonsNum,entity.getChangeVehicle(),entity.getFarmId(),entity.getCoefficient());
                    itemTime=gene.getTime();
                    String time= timeSuplus(gene,itemTime,summonsNum);
//                    String time=DateUtils.minuteSub(itemTime,-summonsNum);
                    entity.setNum(entity.getNum()-summonsNum);
                    entity.setTime(time);
                    list.add(gene);
                }while (entity.getNum()>summonsNum);
            }
        }
        produceDemandList.addAll(list);
        Comparator<Gene> byTime = Comparator.comparing(Gene::getTime);
        //排序后得到的summonsList序列
        produceDemandList= produceDemandList.stream().sorted(byTime).collect(Collectors.toList());
    }

    public List<DropPoint> lightSpectrum(List<DropPoint> summonsList){
        int endFlag=0;
        LockNum lockNum=caculateFitness(summonsList);
        Comparator<DropPoint> byTime = Comparator.comparing(DropPoint::getTime);
        //排序后得到的summonsList序列
        summonsList= summonsList.stream().sorted(byTime).collect(Collectors.toList());
        if(lockNum.getIndex()==null){
            return summonsList;
        }

        List<DropPoint> list=new ArrayList<>();
        List<DropPoint> lastList=new ArrayList<>();
        //找出出异常的时间前的供件情况
        getLastList(list,lastList,summonsList,lockNum);
        lastList=lastList.stream().sorted(byTime).collect(Collectors.toList());

        //从后往前推
        do {
            int splitByRandomNumFlag=0;
            int splitMinPackingFlag=0;
            //先判断是否应该跳出循环
            List<DropPoint> testList=new ArrayList<>();
            for (int i=list.size()-1;i>=0; i--) {
                int flag=0;
                for(DropPoint entity:testList){
                    if(entity.getName().equals(list.get(i).getName())){
                        flag=1;
                    }
                }
                if(flag==0){
                    testList.add(list.get(i));
                }
            }
            for(DropPoint entity:testList){
                if(entity.getSupplySurplusNum()>=minPackingNum){
                    splitMinPackingFlag=1;
                    break;
                }
            }

            if(splitMinPackingFlag==1){
                //按最小包装数进行拆分
                for(int i=list.size()-1;i>=0;i--) {
                    if (list.get(i).getSupplySurplusNum() >= minPackingNum&&list.get(i).getSupplyNum()>=minPackingNum) {
                        //后续的时间全都改变了
                        List<DropPoint> itemList = new ArrayList<>();
                        for (DropPoint entity : summonsList) {
                            if (entity.getName().equals(list.get(i).getName())) {
                                itemList.add(entity);
                            }
                        }
                        for (int ii = 0; ii < itemList.size(); ii++) {
                            int minute = DateUtils.timeDifference(itemList.get(ii).getTime(), list.get(i).getTime());
                            if (minute <= 0) {
                                if (minute == 0) {
                                    itemList.get(ii).setSupplyNum(list.get(i).getSupplyNum() - minPackingNum);
                                    itemList.get(ii).setSupplySurplusNum(list.get(i).getSupplySurplusNum() - minPackingNum);
                                } else {
                                    itemList.get(ii).setSurplusNum(itemList.get(ii - 1).getSupplySurplusNum());
                                    if(itemList.get(ii).getNum()>itemList.get(ii).getSurplusNum()){
                                        String time=timeSuplus(itemList.get(ii),itemList.get(ii).getTime(),itemList.get(ii).getSurplusNum());
                                        itemList.get(ii).setSupplyTime(time);
                                    }else{
                                        String time=timeSuplus(itemList.get(ii),itemList.get(ii).getTime(),itemList.get(ii).getNum());
                                        itemList.get(ii).setSupplyTime(time);
                                    }
                                    //计算满足生产的最少的量
                                    Integer num = itemList.get(ii).getNum() - itemList.get(ii).getSurplusNum();
                                    Integer supplyNum;
                                    Integer supplySurplusNum;
                                    if (num < 0) {
                                        supplyNum = 0;
                                        supplySurplusNum = itemList.get(ii).getSurplusNum() - itemList.get(ii).getNum();
                                    } else if(itemList.get(ii).getChangeVehicle()==0){
                                        supplyNum=minPackingNum*(num/minPackingNum+1);
                                        supplySurplusNum=supplyNum-num;
                                    }else{
                                        supplyNum=summonsNum*(num/summonsNum+1);
                                        supplySurplusNum=supplyNum-num;
                                    }
                                    itemList.get(ii).setSupplyNum(supplyNum);
                                    itemList.get(ii).setSupplySurplusNum(supplySurplusNum);
                                }
                                //改变供件数量后，后续改变后面的供件数以及供件时间
                            }
                        }
                        splitByRandomNumFlag=1;
                        break;
                    }
                }
            }


            //最苛刻的拆分
            if(splitByRandomNumFlag==0){
                List<DropPoint> itemList = new ArrayList<>();
                //存在末尾时间不需要供件的情况
                List<DropPoint> noSupplyList=new ArrayList<>();
                for (int i=list.size()-1;i>=0; i--) {
                    int flag=0;
                    for(DropPoint entity:itemList){
                        if(entity.getName().equals(list.get(i).getName())){
                            flag=1;
                        }
                    }
                    if(flag==0){
                        if(list.get(i).getSupplyNum()>0){
                            itemList.add(list.get(i));
                        }else{
                            noSupplyList.add(list.get(i));
                        }
                    }
                }
                //计算最后一次分配所剩下的数量
                int num=0;
                int needNum=0;
                List<DropPoint> splitList=new ArrayList<>();
                for(DropPoint entity:itemList){
                    num+=entity.getSupplyNum();
                    needNum=needNum+entity.getNum()-entity.getSurplusNum();
                    DropPoint dropPoint=new DropPoint();
                    dropPoint.setName(entity.getName());
                    dropPoint.setNum(entity.getNum()-entity.getSurplusNum());
                    dropPoint.setTime(entity.getSupplyTime());
                    dropPoint.setFarmId(entity.getFarmId());
                    dropPoint.setCoefficient(entity.getCoefficient());
                    splitList.add(dropPoint);
                }
                num-=lockNum.getNum();
                if(num<=0){
                    System.out.println("到货不匹配，请注意细查！");
                    return summonsList;
                }
                if(num>=needNum){
                    middleSplit(noSupplyList,lastList,summonsList,itemList,list,num,needNum);
                }else if(num<needNum){
                    if(lastList.size()==0){
                        System.out.println("到货不匹配，请注意细查！");
                        return null;
                    }
                    GACalculation calculation=new GACalculation();
                    List<Integer> splitNum=calculation.solve(num,splitList);
                    //拆分后回填数目
                    int i=0;
                    for(DropPoint entity:itemList){
                        entity.setSupplyNum(splitNum.get(i));
                        i++;
                    }
                    //@TODO 遗传学拆分后,算出供应的最小时间
                    String min="210010102020";
                    for(DropPoint entity:itemList){
                        String time=timeSuplus(entity,entity.getSupplyTime(),entity.getSupplyNum());
//                        DateUtils.minuteSub(entity.getSupplyTime(),-entity.getSupplyNum());
                        int a=DateUtils.timeDifference(entity.getSupplyTime(),min);
                        if(a>0){
                            min=time;
                        }
                    }
                    String time=arrivalNumList.get(lockNum.getIndex()).getTime();
                    //判断供件是否来得及时
                    int a=DateUtils.timeDifference(min,time);
                    if(a>0){
                        System.out.println("到货不匹配，请注意细查！");
                        return null;
                    }
                    //拆分后数据回填
                    for(DropPoint entity:itemList){
                        int splitN=entity.getNum()-entity.getSupplyNum()-entity.getSurplusNum();
                        entity.setSupplySurplusNum(0);
                        entity.setNum(entity.getSupplyNum()+entity.getSurplusNum());
                        //创建一个新的需求
                        DropPoint dropPoint=new DropPoint();
                        dropPoint.setName(entity.getName());
                        dropPoint.setNum(splitN);
                        dropPoint.setTime(min);
                        dropPoint.setSurplusNum(0);
                        dropPoint.setCoefficient(entity.getCoefficient());
                        dropPoint.setFarmId(entity.getFarmId());
                        dropPoint.setChangeVehicle(entity.getChangeVehicle());
                        summonsList.add(dropPoint);
                    }
                    //重新排序
                    summonsList= summonsList.stream().sorted(byTime).collect(Collectors.toList());
                    List<DropPoint> itemSplitList=new ArrayList<>();
                    for(DropPoint dropPoint:summonsList){
                        if(dropPoint.getName().equals(list.get(list.size()-1).getName())
                                    &&dropPoint.getTime().equals(list.get(list.size()-1).getTime())){
                            itemSplitList.add(dropPoint);
                            break;
                        }else{
                            itemSplitList.add(dropPoint);
                        }
                    }
                    againSplit(summonsList,itemSplitList.size()-noSupplyList.size());
                }
            }
            //对拆分后的结果再检验
            lockNum=caculateFitness(summonsList);
            if(lockNum.getNum()!=null){
                //找出出异常的时间前的供件情况
                list=new ArrayList<>();
                lastList=new ArrayList<>();
                getLastList(list,lastList,summonsList,lockNum);
                lastList=lastList.stream().sorted(byTime).collect(Collectors.toList());
            }else{
                endFlag=1;
            }
        } while(endFlag==0);
        return summonsList;
    }

    /**
     *  针对本次拆分对后续的落点的需求影响
     */
    public void middleSplit(List<DropPoint> noSupplyList,List<DropPoint> lastList,List<DropPoint> summonsList,
                            List<DropPoint> itemList,List<DropPoint> list, int num,int needNum){
        //不应该和并，只能将noSupplyList回溯完，然后去影响lastList中第一个落点
        Comparator<DropPoint> byTime = Comparator.comparing(DropPoint::getTime);
        //以拆分点为临界点，看看是否满足本次生产
        itemList=itemList.stream().sorted(byTime).collect(Collectors.toList());
        List<DropPoint> surplusList=new ArrayList<>();
        //获取本次拆分点后续所需的件
        int lastFlag,surplusDemand=0,lastAll=0;
        for(DropPoint entity:list){
            for(DropPoint en:itemList){
                if(entity.getName().equals(en.getName())&&DateUtils.timeDifference(en.getTime(),entity.getTime())>0){
                            surplusList.add(entity);
                            surplusDemand+=entity.getNum();
                }
            }
        }
        if(surplusDemand+needNum<num){
            Map<String,Integer> surplusMap=new HashMap<>();
            Map<String,Integer> lastMap=new HashMap<>();
            int surplusflag;
            noSupplyList=noSupplyList.stream().sorted(byTime).collect(Collectors.toList());
            for(DropPoint entity:noSupplyList){
                surplusflag=0;
                for(Map.Entry<String, Integer> entry:surplusMap.entrySet()){
                    if(entry.getKey().equals(entity.getName())){
                        surplusflag=1;
                        surplusMap.put(entity.getName(),entry.getValue()+entity.getNum());
                    }
                }
                if(surplusflag==0){
                    surplusMap.put(entity.getName(),entity.getNum());
                }
            }
            for(DropPoint entity:lastList){
                lastAll+=entity.getNum();
                lastFlag=0;
                for(Map.Entry<String, Integer> entry:lastMap.entrySet()){
                    if(entry.getKey().equals(entity.getName())){
                        lastFlag=1;
                        lastMap.put(entity.getName(),entry.getValue()+entity.getNum());
                    }
                }
                if(lastFlag==0){
                    lastMap.put(entity.getName(),entity.getNum());
                }
            }

            //开始拆分
            int endNum=num-surplusDemand-needNum;
            int surplus=endNum;
            double supply;
            for(int i=0;i<itemList.size();i++) {
                int isHaveLastFlag=0;
                for (Map.Entry<String, Integer> entry : lastMap.entrySet()) {
                    isHaveLastFlag=1;
                    if (entry.getKey().equals(itemList.get(i).getName())) {
                        double a = entry.getValue() * 1.0 / lastAll * 1.0;
                        int isHaveSurplus=0;
                        for(Map.Entry<String, Integer> surplusEntry : surplusMap.entrySet()){
                            if (surplusEntry.getKey().equals(itemList.get(i).getName())) {
                                isHaveSurplus=1;
                                supply = surplusEntry.getValue()+itemList.get(i).getNum()-itemList.get(i).getSurplusNum()+ surplus * a;
                                int aaa = new Double(supply).intValue();
                                if (i == itemList.size() - 1) {
                                    aaa = surplusEntry.getValue()+itemList.get(i).getNum()-itemList.get(i).getSurplusNum()+ endNum;
                                } else {
                                    endNum = endNum - (aaa - surplusEntry.getValue()-itemList.get(i).getNum()+itemList.get(i).getSurplusNum());
                                }
                                itemList.get(i).setSupplyNum(aaa);
                                //算出剩下的数量
                                itemList.get(i).setSupplySurplusNum(aaa + itemList.get(i).getSurplusNum() - itemList.get(i).getNum());
                                //影响没有供件的落点
                                int supplySurplusNum = -1;
                                for (DropPoint en : noSupplyList) {
                                    if (en.getName().equals(itemList.get(i).getName())) {
                                        if (supplySurplusNum == -1) {
                                            en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                        } else {
                                            en.setSurplusNum(supplySurplusNum);
                                        }
                                        en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                        //这里可能会出现剩余的数量不满足供件的情况
                                        supplySurplusNum = en.getSupplySurplusNum();
                                    }
                                }
                                //回填下个落点
                                if (lastList.size() > 0) {
                                    for (DropPoint en : lastList) {
                                        if (en.getName().equals(itemList.get(i).getName())) {
                                            if (noSupplyList.size() > 0 && supplySurplusNum != -1) {
                                                en.setSurplusNum(supplySurplusNum);
                                            } else {
                                                en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                            }
                                            en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        if(isHaveSurplus==0){
                            supply = itemList.get(i).getNum()-itemList.get(i).getSurplusNum()+ surplus * a;
                            int aaa = new Double(supply).intValue();
                            if (i == itemList.size() - 1) {
                                aaa = itemList.get(i).getNum()-itemList.get(i).getSurplusNum()+ endNum;
                            } else {
                                endNum = endNum - (aaa - itemList.get(i).getNum()+itemList.get(i).getSurplusNum());
                            }
                            itemList.get(i).setSupplyNum(aaa);
                            //算出剩下的数量
                            itemList.get(i).setSupplySurplusNum(aaa + itemList.get(i).getSurplusNum() - itemList.get(i).getNum());
                            //影响没有供件的落点
                            int supplySurplusNum = -1;
                            for (DropPoint en : noSupplyList) {
                                if (en.getName().equals(itemList.get(i).getName())) {
                                    if (supplySurplusNum == -1) {
                                        en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                    } else {
                                        en.setSurplusNum(supplySurplusNum);
                                    }
                                    en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                    //这里可能会出现剩余的数量不满足供件的情况
                                    supplySurplusNum = en.getSupplySurplusNum();
                                }
                            }
                            //回填下个落点
                            if (lastList.size() > 0) {
                                for (DropPoint en : lastList) {
                                    if (en.getName().equals(itemList.get(i).getName())) {
                                        if (noSupplyList.size() > 0 && supplySurplusNum != -1) {
                                            en.setSurplusNum(supplySurplusNum);
                                        } else {
                                            en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                        }
                                        en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if(isHaveLastFlag==0){
                    int isHaveSurplusFlag=0;
                    for(Map.Entry<String, Integer> surplusEntry : surplusMap.entrySet()){
                        if (surplusEntry.getKey().equals(itemList.get(i).getName())) {
                            isHaveSurplusFlag=1;
                            int demandNum=surplusEntry.getValue()+itemList.get(i).getNum()-itemList.get(i).getSurplusNum();
                            itemList.get(i).setSupplyNum(demandNum);
                            //算出剩下的数量
                            itemList.get(i).setSupplySurplusNum(demandNum+itemList.get(i).getSurplusNum()- itemList.get(i).getNum());
                            //影响没有供件的落点
                            int supplySurplusNum = -1;
                            for (DropPoint en : noSupplyList) {
                                if (en.getName().equals(itemList.get(i).getName())) {
                                    if (supplySurplusNum == -1) {
                                        en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                    } else {
                                        en.setSurplusNum(supplySurplusNum);
                                    }
                                    en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                    //这里可能会出现剩余的数量不满足供件的情况
                                    supplySurplusNum = en.getSupplySurplusNum();
                                }
                            }
                            //回填下个落点
                            if (lastList.size() > 0) {
                                for (DropPoint en : lastList) {
                                    if (en.getName().equals(itemList.get(i).getName())) {
                                        if (noSupplyList.size() > 0 && supplySurplusNum != -1) {
                                            en.setSurplusNum(supplySurplusNum);
                                        } else {
                                            en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                        }
                                        en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                    if(isHaveSurplusFlag==0){
                        int demandNum=itemList.get(i).getNum()-itemList.get(i).getSurplusNum();
                        itemList.get(i).setSupplyNum(demandNum);
                        //算出剩下的数量
                        itemList.get(i).setSupplySurplusNum(demandNum- itemList.get(i).getNum());
                        //影响没有供件的落点
                        int supplySurplusNum = -1;
                        for (DropPoint en : noSupplyList) {
                            if (en.getName().equals(itemList.get(i).getName())) {
                                if (supplySurplusNum == -1) {
                                    en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                } else {
                                    en.setSurplusNum(supplySurplusNum);
                                }
                                en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                //这里可能会出现剩余的数量不满足供件的情况
                                supplySurplusNum = en.getSupplySurplusNum();
                            }
                        }
                        //回填下个落点
                        if (lastList.size() > 0) {
                            for (DropPoint en : lastList) {
                                if (en.getName().equals(itemList.get(i).getName())) {
                                    if (noSupplyList.size() > 0 && supplySurplusNum != -1) {
                                        en.setSurplusNum(supplySurplusNum);
                                    } else {
                                        en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                    }
                                    en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else if(surplusDemand+needNum==num){
            Map<String,Integer> surplusMap=new HashMap<>();
            int surplusflag;
            noSupplyList=noSupplyList.stream().sorted(byTime).collect(Collectors.toList());
            for(DropPoint entity:noSupplyList){
                surplusflag=0;
                for(Map.Entry<String, Integer> entry:surplusMap.entrySet()){
                    if(entry.getKey().equals(entity.getName())){
                        surplusflag=1;
                        surplusMap.put(entity.getName(),entry.getValue()+entity.getNum());
                    }
                }
                if(surplusflag==0){
                    surplusMap.put(entity.getName(),entity.getNum());
                }
            }
            for(int i=0;i<itemList.size();i++) {
                int isHaveSurplus=0;
                for(Map.Entry<String, Integer> surplusEntry : surplusMap.entrySet()) {
                    if (surplusEntry.getKey().equals(itemList.get(i).getName())) {
                        isHaveSurplus=1;
                        int demandNum=surplusEntry.getValue()+itemList.get(i).getNum()-itemList.get(i).getSurplusNum();
                        itemList.get(i).setSupplyNum(demandNum);
                        //算出剩下的数量
                        itemList.get(i).setSupplySurplusNum(demandNum+itemList.get(i).getSurplusNum()- itemList.get(i).getNum());
                        //影响没有供件的落点
                        int supplySurplusNum = -1;
                        for (DropPoint en : noSupplyList) {
                            if (en.getName().equals(itemList.get(i).getName())) {
                                if (supplySurplusNum == -1) {
                                    en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                } else {
                                    en.setSurplusNum(supplySurplusNum);
                                }
                                en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                //这里可能会出现剩余的数量不满足供件的情况
                                supplySurplusNum = en.getSupplySurplusNum();
                            }
                        }
                        //回填下个落点
                        if (lastList.size() > 0) {
                            for (DropPoint en : lastList) {
                                if (en.getName().equals(itemList.get(i).getName())) {
                                    if (noSupplyList.size() > 0 && supplySurplusNum != -1) {
                                        en.setSurplusNum(supplySurplusNum);
                                    } else {
                                        en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                    }
                                    en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                    break;
                                }
                            }
                        }
                    }
                }
                if(isHaveSurplus==0){
                    int demandNum=itemList.get(i).getNum()-itemList.get(i).getSurplusNum();
                    itemList.get(i).setSupplyNum(demandNum);
                    //算出剩下的数量
                    itemList.get(i).setSupplySurplusNum(demandNum+itemList.get(i).getSurplusNum()- itemList.get(i).getNum());
                    //影响没有供件的落点
                    int supplySurplusNum = -1;
                    for (DropPoint en : noSupplyList) {
                        if (en.getName().equals(itemList.get(i).getName())) {
                            if (supplySurplusNum == -1) {
                                en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                            } else {
                                en.setSurplusNum(supplySurplusNum);
                            }
                            en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                            //这里可能会出现剩余的数量不满足供件的情况
                            supplySurplusNum = en.getSupplySurplusNum();
                        }
                    }
                    //回填下个落点
                    if (lastList.size() > 0) {
                        for (DropPoint en : lastList) {
                            if (en.getName().equals(itemList.get(i).getName())) {
                                if (noSupplyList.size() > 0 && supplySurplusNum != -1) {
                                    en.setSurplusNum(supplySurplusNum);
                                } else {
                                    en.setSurplusNum(itemList.get(i).getSupplySurplusNum());
                                }
                                en.setSupplySurplusNum(en.getSupplyNum() + en.getSurplusNum() - en.getNum());
                                break;
                            }
                        }
                    }
                }
            }
        }else{

        }

        //后续时间再分配问题
        List<DropPoint> itemSplitList=new ArrayList<>();
        for(DropPoint dropPoint:summonsList){
            if(dropPoint.getName().equals(list.get(list.size()-1).getName())
                    &&dropPoint.getTime().equals(list.get(list.size()-1).getTime())){
                itemSplitList.add(dropPoint);
                break;
            }else{
                itemSplitList.add(dropPoint);
            }
        }
        againSplit(summonsList,itemSplitList.size());
    }

    /**
     * 根据index 判断某个到货时间段后续的供件需求
     */
    public void getLastList(List<DropPoint> list,List<DropPoint> lastList,List<DropPoint> summonsList,LockNum lockNum){

        int isHaveLastFlag=0;
        for(int i=lockNum.getIndex();i<arrivalNumList.size();i++){
            for(DropPoint entity:summonsList) {
                int a=DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(i).getTime());
                if(a>0&&isHaveLastFlag==0){
                    list.add(entity);
                }else{
                    if(i+1==arrivalNumList.size()){
                        if(DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(i).getTime())<0){
                            lastList.add(entity);
                        }
                    }else{
                        if(DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(i+1).getTime())>=0
                                &&DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(i).getTime())<0){
                            lastList.add(entity);
                        }
                    }
                }
            }
            if(lastList.size()>0){
                break;
            }else{
                isHaveLastFlag=1;
            }
        }
        if(lockNum.getIndex()==arrivalNumList.size()){
            for(DropPoint dropPoint:summonsList){
                list.add(dropPoint);
            }
        }
    }


    public void againSplit(List<DropPoint> summonsList,int listSize){
        //改变下一轮的供件特点
        for(int i=listSize;i<summonsList.size();i++){
            //计算什么时候供件
            String time=supplyTime(summonsList.get(i));
            summonsList.get(i).setSupplyTime(time);
            //计算满足生产的最少的量
            int flag=summonsList.get(i).getSurplusNum()-summonsList.get(i).getNum();
            Integer num=summonsList.get(i).getNum()-summonsList.get(i).getSurplusNum();
            Integer supplyNum;
            Integer supplySurplusNum;
            if(flag>=0){
                supplyNum=0;
                supplySurplusNum=summonsList.get(i).getSurplusNum()-summonsList.get(i).getNum();
            }else if(summonsList.get(i).getChangeVehicle()==0){
                if(num/minPackingNum==0){
                    supplyNum=minPackingNum;
                }else{
                    supplyNum=minPackingNum*(num/minPackingNum);
                }
                supplySurplusNum=supplyNum-num;
            }else{
                if(num/summonsNum==0){
                    supplyNum=summonsNum;
                }else{
                    supplyNum=summonsNum*(num/summonsNum);
                }
                supplySurplusNum=supplyNum-num;
            }
            summonsList.get(i).setSupplyNum(supplyNum);
            summonsList.get(i).setSupplySurplusNum(supplySurplusNum);
            //回填下个时间点的剩余数量
            for(int j=i+1;j<summonsList.size();j++){
                if(summonsList.get(i).getName().equals(summonsList.get(j).getName())){
                    summonsList.get(j).setSurplusNum(supplySurplusNum);
                    break;
                }
            }
        }
    }

    public LockNum caculateFitness(List<DropPoint> list){

        int itemWarePlus=wareHouseSurplus;
        LockNum lockNum=new LockNum();

        //排序
        Comparator<DropPoint> byTime = Comparator.comparing(DropPoint::getTime);
        //排序后得到的summonsList序列
        list= list.stream().sorted(byTime).collect(Collectors.toList());
        int arrivalNum=arrivalNumList.get(0).getNum();
        int num=0;
        int i=0;
        for(DropPoint entity:list){
            if(i<arrivalNumList.size()&&DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(i).getTime())<0){
                if(num!=0&&arrivalNum>=num){
                    wareHouseSurplus=arrivalNum-num;
                    arrivalNum=arrivalNumList.get(i).getNum()+wareHouseSurplus;
                    //是否接下来都是到货的时间
                    for(int j=i+1;j<arrivalNumList.size();j++){
                        if(DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(j).getTime())<0){
                            arrivalNum+=arrivalNumList.get(j).getNum();
                            i++;
                        }
                    }
                    num=entity.getSupplyNum();
                }else if(num!=0&&arrivalNum<num){
                    lockNum.setIndex(i);
                    lockNum.setNum(num-arrivalNum);
                    return lockNum;
                }else{
                    if(i!=0){
                        wareHouseSurplus=arrivalNum-num;
                        arrivalNum=arrivalNumList.get(i).getNum()+wareHouseSurplus;
                        //是否接下来都是到货的时间
                        for(int j=i+1;j<arrivalNumList.size();j++){
                            if(DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(j).getTime())<0){
                                arrivalNum+=arrivalNumList.get(j).getNum();
                                i++;
                            }
                        }
                        num=entity.getSupplyNum();
                    }else{
                        num=entity.getSupplyNum();
                        arrivalNum=arrivalNumList.get(i).getNum()+wareHouseSurplus;
                        //是否接下来都是到货的时间
                        for(int j=i+1;j<arrivalNumList.size();j++){
                            if(DateUtils.timeDifference(entity.getSupplyTime(),arrivalNumList.get(j).getTime())<0){
                                arrivalNum+=arrivalNumList.get(j).getNum();
                                i++;
                            }
                        }
                    }
                }
                i++;
            }else{
                num+=entity.getSupplyNum();
            }
        }
        //最后一次的比较
        if(arrivalNum<num){
            lockNum.setIndex(i);
            lockNum.setNum(num-arrivalNum);
            return lockNum;
        }
        wareHouseSurplus=itemWarePlus;
        return lockNum;
    }

    //按最优，传票数进行分配
    public List<DropPoint> splitBySummonsNum(){

        //第一步，根据配送线路,落点，供件时间，顺序排序
        Comparator<Gene> byTime = Comparator.comparing(Gene::getTime);
        //排序后得到的summonsList序列
        produceDemandList=produceDemandList.stream().sorted(byTime).collect(Collectors.toList());
        Set<String> dropPoint=new HashSet();
        for(Gene entity:produceDemandList){
            dropPoint.add(entity.getDropPoint());
        }
        List<DropPoint> summonsSolve=new ArrayList<>();
        for(String entity:dropPoint){
            List<DropPoint> itemList=new ArrayList<>();
            for(Gene gene:produceDemandList){
                if(entity.equals(gene.dropPoint)){
                    DropPoint dp=new DropPoint();
                    dp.setName(entity);
                    dp.setNum(gene.getNum());
                    dp.setTime(gene.getTime());
                    dp.setFarmId(gene.getFarmId());
                    dp.setChangeVehicle(gene.getChangeVehicle());
                    dp.setCoefficient(gene.getCoefficient());
                    //回填该落点的剩余数量
                    if(itemList.size()==0){
                        for(DropPointSurplus surplus:dropPointList){
                            if(entity.equals(surplus.getName())){
                                dp.setSurplusNum(surplus.getSurplusNum());
                                break;
                            }
                        }
                    }else{
                        dp.setSurplusNum(itemList.get(itemList.size()-1).getSupplySurplusNum());
                    }
                    String time= supplyTime(dp);
                    dp.setSupplyTime(time);

                    //计算满足生产的最少的量
                    Integer num=gene.getNum()-dp.getSurplusNum();
                    Integer supplyNum;
                    Integer supplySurplusNum;
                    if(num<=0){
                        supplyNum=0;
                        supplySurplusNum=dp.getSurplusNum()-gene.getNum();
                    }else if(gene.getChangeVehicle()==0){
                        if(num/minPackingNum==0){
                            supplyNum=minPackingNum;
                        }else{
                            supplyNum=minPackingNum*(num/minPackingNum);
                        }
                        supplySurplusNum=supplyNum-num;
                    }else{
                        if(num/summonsNum==0){
                            supplyNum=summonsNum;
                        }else{
                            supplyNum=summonsNum*(num/summonsNum);
                        }
                        supplySurplusNum=supplyNum-num;
                    }
                    dp.setSupplyNum(supplyNum);
                    dp.setSupplySurplusNum(supplySurplusNum);
                    itemList.add(dp);
                }
            }
            summonsSolve.addAll(itemList);
        }
        return summonsSolve;
    }

    public String supplyTime(DropPoint dp){
        //计算什么时候供件
        Integer platForm=0;
        for(ChainspeedEntity chainspeed:chainspeedList){
//            System.out.println(chainspeed.getProductionDate()+"    "+dp.getTime().substring(8));
//            System.out.println(chainspeed.getFarmId()+"    "+dp.getFarmId());
            //找到台数
            if(chainspeed.getFarmId().equals(dp.getFarmId())
                    &&chainspeed.getProductionDate().equals(dp.getTime().substring(8))){
                platForm=chainspeed.getPlatform();
                break;
            }
        }
        if(platForm==0){

        }
        String time="";
        if(dp.getNum()>dp.getSurplusNum()){
            platForm+=dp.getSurplusNum()/dp.getCoefficient();
            //找到下次供件时间
            for(ChainspeedEntity chainspeed:chainspeedList){
                //找到台数
                if(chainspeed.getFarmId().equals(dp.getFarmId())
                        &&chainspeed.getPlatform().equals(platForm)){
                    time=chainspeed.getProductionDate();
                    break;
                }
            }
        }else{
            platForm+=dp.getNum()/dp.getCoefficient();
            //找到下次供件时间
            for(ChainspeedEntity chainspeed:chainspeedList){
                //找到台数
                if(chainspeed.getFarmId().equals(dp.getFarmId())
                        &&chainspeed.getPlatform().equals(platForm)){
                    time=chainspeed.getProductionDate();
                    break;
                }
            }
        }
        if(time.equals("")){
            System.out.println();
        }
        //注意是否跨越第二天
        Long planTime=Long.parseLong(dp.getTime().substring(8));
        Long supplyTime=Long.parseLong(time);
        if(planTime>supplyTime){
            //日期往后叠加一天
            String aa=DateUtils.minuteSub(dp.getTime(),-24*60);
            time=aa.substring(0,8)+time;
            return time;
        }
        time=dp.getTime().substring(0,8)+time;
        return time;
    }

    public String timeSuplus(DropPoint dp,String startTime,Integer num){
        //计算什么时候供件
        Integer platForm=0;
        for(ChainspeedEntity chainspeed:chainspeedList){
//            System.out.println(chainspeed.getProductionDate());
            //找到台数
            if(chainspeed.getFarmId().equals(dp.getFarmId())
                    &&chainspeed.getProductionDate().equals(startTime.substring(8))){
                platForm=chainspeed.getPlatform();
                break;
            }
        }
        if(platForm==0){
        }
        String time="";
        platForm+=num/dp.getCoefficient();
        //找到下次供件时间
        for(ChainspeedEntity chainspeed:chainspeedList){
            //找到台数
            if(chainspeed.getFarmId().equals(dp.getFarmId())
                    &&chainspeed.getPlatform().equals(platForm)){
                time=chainspeed.getProductionDate();
                break;
            }
        }
        //注意是否跨越第二天
        Long planTime=Long.parseLong(dp.getTime().substring(8));
        Long supplyTime=Long.parseLong(time);
        if(planTime>supplyTime){
            //日期往后叠加一天
            String aa=DateUtils.minuteSub(dp.getTime(),-24*60);
            time=aa.substring(0,8)+time;
            return time;
        }
        time=dp.getTime().substring(0,8)+time;
        return time;
    }

    public String timeSuplus(Gene dp,String startTime,Integer num){
        //计算什么时候供件
        Integer platForm=0;
        for(ChainspeedEntity chainspeed:chainspeedList){
            System.out.println(chainspeed.getProductionDate());
            //找到台数
            if(chainspeed.getFarmId().equals(dp.getFarmId())
                    &&chainspeed.getProductionDate().equals(startTime.substring(8))){
                platForm=chainspeed.getPlatform();
                break;
            }
        }
        if(platForm==0){
        }
        String time="";
        platForm+=num/dp.getCoefficient();
        //找到下次供件时间
        for(ChainspeedEntity chainspeed:chainspeedList){
            //找到台数
            if(chainspeed.getFarmId().equals(dp.getFarmId())
                    &&chainspeed.getPlatform().equals(platForm)){
                time=chainspeed.getProductionDate();
                break;
            }
        }
        //注意是否跨越第二天
        Long planTime=Long.parseLong(dp.getTime().substring(8));
        Long supplyTime=Long.parseLong(time);
        if(planTime>supplyTime){
            //日期往后叠加一天
            String aa=DateUtils.minuteSub(dp.getTime(),-24*60);
            time=aa.substring(0,8)+time;
            return time;
        }
        time=dp.getTime().substring(0,8)+time;
        return time;
    }


    class Gene{
        private String dropPoint;
        private String time;
        private Integer num;
        private Integer changeVehicle;
        private Integer farmId;
        private Integer coefficient;

        public Gene(String dropPoint,String time,Integer num,Integer changeVehicle,Integer farmId,Integer coefficient){
            this.dropPoint=dropPoint;
            this.time=time;
            this.num=num;
            this.changeVehicle=changeVehicle;
            this.farmId=farmId;
            this.coefficient=coefficient;
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

        public Integer getChangeVehicle() {
            return changeVehicle;
        }

        public void setChangeVehicle(Integer type) {
            this.changeVehicle = type;
        }

        public Integer getFarmId() {
            return farmId;
        }

        public void setFarmId(Integer farmId) {
            this.farmId = farmId;
        }

        public Integer getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(Integer coefficient) {
            this.coefficient = coefficient;
        }
    }

    class SupplyNum{
        private String time;
        private Integer num;
        private Integer type;

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

    class DropPointSurplus{
        private String name;
        private Integer time;
        private Integer surplusNum;

        public DropPointSurplus(String name,Integer surplusNum){
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
            return time;
        }

        public void setSurplusTimeNum(Integer time) {
            this.time = time;
        }
    }
}
