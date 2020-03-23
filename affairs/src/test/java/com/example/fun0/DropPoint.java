package com.example.fun0;

import lombok.Data;

@Data
public class DropPoint {
    private String name;
    private String time;
    private Integer num;
    private Integer surplusNum;

    private String supplyTime;
    private Integer supplyNum;
    private Integer supplySurplusNum;
    //是否需要转车型
    public Integer changeVehicle;

}
