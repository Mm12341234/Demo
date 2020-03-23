package com.example.entity;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Author Liumq
 * Date  2020-03-05
 */
public class ProducePlanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

       private Integer id;
       private Integer year;
       private Integer week;
       private Integer lineId;
       private Integer farmId;
       private String parts;
       private String color;
       private String factoryid;
       private String dropPoint;
       private Date startTime;
       private Integer demandNum;
       private String style;
       private Integer minpacking;
       private Integer summonNum;
       private Integer deviation;
       private String ismany;
       private Integer coefficient;
       private Integer dealType;
       private String carType;
       private String carStyle;
       private String carColor;
       private String nextCarType;
       private String nextCarColor;
       private String nextCarStyle;
       private Integer nextCarTypeOptimal;

       public void setId(Integer id) {
          this.id = id;
       }
       public Integer getId() {
          return id;
       }
       public void setYear(Integer year) {
          this.year = year;
       }
       public Integer getYear() {
          return year;
       }
       public void setWeek(Integer week) {
          this.week = week;
       }
       public Integer getWeek() {
          return week;
       }
       public void setLineId(Integer lineId) {
          this.lineId = lineId;
       }
       public Integer getLineId() {
          return lineId;
       }
       public void setFarmId(Integer farmId) {
          this.farmId = farmId;
       }
       public Integer getFarmId() {
          return farmId;
       }
       public void setParts(String parts) {
          this.parts = parts;
       }
       public String getParts() {
          return parts;
       }
       public void setColor(String color) {
          this.color = color;
       }
       public String getColor() {
          return color;
       }
       public void setFactoryid(String factoryid) {
          this.factoryid = factoryid;
       }
       public String getFactoryid() {
          return factoryid;
       }
       public void setDropPoint(String dropPoint) {
          this.dropPoint = dropPoint;
       }
       public String getDropPoint() {
          return dropPoint;
       }
       public void setStartTime(Date startTime) {
          this.startTime = startTime;
       }
       public String getStartTime() {
          SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmm");
          return df.format(startTime);
       }
       public void setDemandNum(Integer demandNum) {
          this.demandNum = demandNum;
       }
       public Integer getDemandNum() {
          return demandNum;
       }
       public void setStyle(String style) {
          this.style = style;
       }
       public String getStyle() {
          return style;
       }
       public void setMinpacking(Integer minpacking) {
          this.minpacking = minpacking;
       }
       public Integer getMinpacking() {
          return minpacking;
       }
       public void setSummonNum(Integer summonNum) {
          this.summonNum = summonNum;
       }
       public Integer getSummonNum() {
          return summonNum;
       }
       public void setDeviation(Integer deviation) {
          this.deviation = deviation;
       }
       public Integer getDeviation() {
          return deviation;
       }
       public void setIsmany(String ismany) {
          this.ismany = ismany;
       }
       public String getIsmany() {
          return ismany;
       }
       public void setCoefficient(Integer coefficient) {
          this.coefficient = coefficient;
       }
       public Integer getCoefficient() {
          return coefficient;
       }
       public void setDealType(Integer dealType) {
          this.dealType = dealType;
       }
       public Integer getDealType() {
          return dealType;
       }
       public void setCarType(String carType) {
          this.carType = carType;
       }
       public String getCarType() {
          return carType;
       }
       public void setCarStyle(String carStyle) {
          this.carStyle = carStyle;
       }
       public String getCarStyle() {
          return carStyle;
       }
       public void setCarColor(String carColor) {
          this.carColor = carColor;
       }
       public String getCarColor() {
          return carColor;
       }
       public void setNextCarType(String nextCarType) {
          this.nextCarType = nextCarType;
       }
       public String getNextCarType() {
          return nextCarType;
       }
       public void setNextCarColor(String nextCarColor) {
          this.nextCarColor = nextCarColor;
       }
       public String getNextCarColor() {
          return nextCarColor;
       }
       public void setNextCarStyle(String nextCarStyle) {
          this.nextCarStyle = nextCarStyle;
       }
       public String getNextCarStyle() {
          return nextCarStyle;
       }
       public void setNextCarTypeOptimal(Integer nextCarTypeOptimal) {
          this.nextCarTypeOptimal = nextCarTypeOptimal;
       }
       public Integer getNextCarTypeOptimal() {
          return nextCarTypeOptimal;
       }
}