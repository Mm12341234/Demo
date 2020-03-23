package com.example.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * Author Liumq
 * Date  2020-01-14
 */
@Data
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer width;
    private Integer depth;
    private Integer height;
    private Integer type;
    private Integer num;

    public enum ContentType{
        crate(0,"周转箱"),
        pallet(1,"托盘");

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        ContentType(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
