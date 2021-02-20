package com.zhys.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    //根据开始月份和终止月份获取范围内所有月份
    public static List<String> getMonths(String monthStart, String monthEnd){
        List<String> monthList = new ArrayList<>();
        try {
            Date start = new SimpleDateFormat("yyyy-MM").parse(monthStart);
            Date end = new SimpleDateFormat("yyyy-MM").parse(monthEnd);
            Calendar calendar = Calendar.getInstance();//定义日期实例
            calendar.setTime(start);//设置日期起始时间
            end.setHours(1);  //将终止月份时间设置为一点以与开始月份时间进行比较
            while (calendar.getTime().before(end)) {//判断是否到结束月份
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String str = sdf.format(calendar.getTime());
                str = str.replace("-", "");
                System.out.println(str+"====================================");
                monthList.add(str);
                calendar.add(Calendar.MONTH, 1);//进行当前日期月份加1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthList;
    }

}
