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
public class ChainspeedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

       private Integer platform;
       private String productionDate;
       private String edit;
       private Integer isuse;
       private Integer id;
       private String line;
       private Integer lineId;
       private Integer farmId;

       public void setPlatform(Integer platform) {
          this.platform = platform;
       }
       public Integer getPlatform() {
          return platform;
       }
       public void setProductionDate(String productionDate) {
          this.productionDate = productionDate;
       }
       public String getProductionDate() {
          String time=productionDate.split(":")[0]+productionDate.split(":")[1];
          return time;
       }
       public void setEdit(String edit) {
          this.edit = edit;
       }
       public String getEdit() {
          return edit;
       }
       public void setIsuse(Integer isuse) {
          this.isuse = isuse;
       }
       public Integer getIsuse() {
          return isuse;
       }
       public void setId(Integer id) {
          this.id = id;
       }
       public Integer getId() {
          return id;
       }
       public void setLine(String line) {
          this.line = line;
       }
       public String getLine() {
          return line;
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
}