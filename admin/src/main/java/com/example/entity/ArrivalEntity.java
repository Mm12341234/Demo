package com.example.entity;

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Author Liumq
 * Date  2020-03-05
 */
public class ArrivalEntity implements Serializable {
    private static final long serialVersionUID = 1L;

       private Integer id;
       private String ncome;
       private String receiptplace;
       private String parts;
       private String color;
       private String plancode;
       private String receivingDate;
       private String receivingTime;
       private String orderQuantity;
       private String quantityAdjusted;
       private String completionDate;
       private String summonsnumber;
       private String summonsPublication;
       private String arrivalRules;
       private String orderPackingNumber;
       private String dropPoint;
       private String tailProcessingDistinguishing;
       private String classification;
       private String productionPlanNumber;
       private String typeDerive;
       private String optionalCode;
       private String bodyColor;
       private String vehiclecode;
       private String receivingType;
       private String plannedProductQuantity;
       private String plannedDate;
       private String plannedTime;
       private String particularYear;
       private String weeklyTimes;
       private String nfrom;
       private String nto;
       private String bearNumber;
       private String line;
       private Date receivingDateTime;
       private Integer lineId;
       private Integer farmId;
       private String receivingDay;

       public void setId(Integer id) {
          this.id = id;
       }
       public Integer getId() {
          return id;
       }
       public void setNcome(String ncome) {
          this.ncome = ncome;
       }
       public String getNcome() {
          return ncome;
       }
       public void setReceiptplace(String receiptplace) {
          this.receiptplace = receiptplace;
       }
       public String getReceiptplace() {
          return receiptplace;
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
       public void setPlancode(String plancode) {
          this.plancode = plancode;
       }
       public String getPlancode() {
          return plancode;
       }
       public void setReceivingDate(String receivingDate) {
          this.receivingDate = receivingDate;
       }
       public String getReceivingDate() {
          return receivingDate;
       }
       public void setReceivingTime(String receivingTime) {
          this.receivingTime = receivingTime;
       }
       public String getReceivingTime() {
          return receivingTime;
       }
       public void setOrderQuantity(String orderQuantity) {
          this.orderQuantity = orderQuantity;
       }
       public String getOrderQuantity() {
          return orderQuantity;
       }
       public void setQuantityAdjusted(String quantityAdjusted) {
          this.quantityAdjusted = quantityAdjusted;
       }
       public String getQuantityAdjusted() {
          return quantityAdjusted;
       }
       public void setCompletionDate(String completionDate) {
          this.completionDate = completionDate;
       }
       public String getCompletionDate() {
          return completionDate;
       }
       public void setSummonsnumber(String summonsnumber) {
          this.summonsnumber = summonsnumber;
       }
       public String getSummonsnumber() {
          return summonsnumber;
       }
       public void setSummonsPublication(String summonsPublication) {
          this.summonsPublication = summonsPublication;
       }
       public String getSummonsPublication() {
          return summonsPublication;
       }
       public void setArrivalRules(String arrivalRules) {
          this.arrivalRules = arrivalRules;
       }
       public String getArrivalRules() {
          return arrivalRules;
       }
       public void setOrderPackingNumber(String orderPackingNumber) {
          this.orderPackingNumber = orderPackingNumber;
       }
       public String getOrderPackingNumber() {
          return orderPackingNumber;
       }
       public void setDropPoint(String dropPoint) {
          this.dropPoint = dropPoint;
       }
       public String getDropPoint() {
          return dropPoint;
       }
       public void setTailProcessingDistinguishing(String tailProcessingDistinguishing) {
          this.tailProcessingDistinguishing = tailProcessingDistinguishing;
       }
       public String getTailProcessingDistinguishing() {
          return tailProcessingDistinguishing;
       }
       public void setClassification(String classification) {
          this.classification = classification;
       }
       public String getClassification() {
          return classification;
       }
       public void setProductionPlanNumber(String productionPlanNumber) {
          this.productionPlanNumber = productionPlanNumber;
       }
       public String getProductionPlanNumber() {
          return productionPlanNumber;
       }
       public void setTypeDerive(String typeDerive) {
          this.typeDerive = typeDerive;
       }
       public String getTypeDerive() {
          return typeDerive;
       }
       public void setOptionalCode(String optionalCode) {
          this.optionalCode = optionalCode;
       }
       public String getOptionalCode() {
          return optionalCode;
       }
       public void setBodyColor(String bodyColor) {
          this.bodyColor = bodyColor;
       }
       public String getBodyColor() {
          return bodyColor;
       }
       public void setVehiclecode(String vehiclecode) {
          this.vehiclecode = vehiclecode;
       }
       public String getVehiclecode() {
          return vehiclecode;
       }
       public void setReceivingType(String receivingType) {
          this.receivingType = receivingType;
       }
       public String getReceivingType() {
          return receivingType;
       }
       public void setPlannedProductQuantity(String plannedProductQuantity) {
          this.plannedProductQuantity = plannedProductQuantity;
       }
       public String getPlannedProductQuantity() {
          return plannedProductQuantity;
       }
       public void setPlannedDate(String plannedDate) {
          this.plannedDate = plannedDate;
       }
       public String getPlannedDate() {
          return plannedDate;
       }
       public void setPlannedTime(String plannedTime) {
          this.plannedTime = plannedTime;
       }
       public String getPlannedTime() {
          return plannedTime;
       }
       public void setParticularYear(String particularYear) {
          this.particularYear = particularYear;
       }
       public String getParticularYear() {
          return particularYear;
       }
       public void setWeeklyTimes(String weeklyTimes) {
          this.weeklyTimes = weeklyTimes;
       }
       public String getWeeklyTimes() {
          return weeklyTimes;
       }
       public void setNfrom(String nfrom) {
          this.nfrom = nfrom;
       }
       public String getNfrom() {
          return nfrom;
       }
       public void setNto(String nto) {
          this.nto = nto;
       }
       public String getNto() {
          return nto;
       }
       public void setBearNumber(String bearNumber) {
          this.bearNumber = bearNumber;
       }
       public String getBearNumber() {
          return bearNumber;
       }
       public void setLine(String line) {
          this.line = line;
       }
       public String getLine() {
          return line;
       }
       public void setReceivingDateTime(Date receivingDateTime) {
          this.receivingDateTime = receivingDateTime;
       }
       public Date getReceivingDateTime() {
          return receivingDateTime;
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
       public void setReceivingDay(String receivingDay) {
          this.receivingDay = receivingDay;
       }
       public String getReceivingDay() {
          return receivingDay;
       }
}