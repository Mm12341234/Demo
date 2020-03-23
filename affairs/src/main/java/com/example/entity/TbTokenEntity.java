package com.example.entity;

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Author Liumq
 * Date  2019-11-05
 */
public class TbTokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

       private Long userId;
       private String token;
       private Date expireTime;
       private Date updateTime;

       public void setUserId(Long userId) {
          this.userId = userId;
       }
       public Long getUserId() {
          return userId;
       }
       public void setToken(String token) {
          this.token = token;
       }
       public String getToken() {
          return token;
       }
       public void setExpireTime(Date expireTime) {
          this.expireTime = expireTime;
       }
       public Date getExpireTime() {
          return expireTime;
       }
       public void setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
       }
       public Date getUpdateTime() {
          return updateTime;
       }
}