package top.anets.utils;

import io.swagger.models.auth.In;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {
    public static String subDays(Integer day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -day);
        return dateToString(calendar.getTime());
    }
    public static String transforAdd(String eleTime,int days){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(stringToDate(eleTime));
        calendar.add(Calendar.DATE, days);
        return dateToString(calendar.getTime());
    }
    public static String transfor(String eleTime){
        return dateToString(stringToDate(eleTime));
    }
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
    public static Date stringToDate(String eleTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        String time = eleTime.replace("Z", " UTC");
        Date date = null;
        try {
             date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
