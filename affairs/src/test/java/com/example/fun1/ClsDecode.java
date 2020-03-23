package com.example.fun1;

import java.util.ArrayList;
import java.util.List;

//解码
public class ClsDecode {
    //单个染色体解码
    public List<Integer> decode(String single,int GENE,int dropNum,int arrivalNum){
        List<Integer> numList=new ArrayList<>();
        int beforIndex=0;
        int afterIndex=GENE/dropNum;
        int flag=GENE/dropNum;
        for(int i=0;i<dropNum-1;i++){
            int num=Integer.parseInt(single.substring(beforIndex,afterIndex),2);
            numList.add(num);
            beforIndex+=flag;
            afterIndex+=flag;
        }
        List<Integer> end=new ArrayList<>();
        for(int i=0;i<numList.size();i++) {
            Double num = numList.get(i) * (arrivalNum * 1.0) / (Math.pow(2, GENE / dropNum) - 1);
            end.add(num.intValue());
        }
        return end;
    }
}