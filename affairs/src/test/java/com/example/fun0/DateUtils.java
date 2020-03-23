package com.example.fun0;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

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
//        num = num % 1440;
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
