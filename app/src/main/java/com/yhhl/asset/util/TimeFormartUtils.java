package com.yhhl.asset.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * function ：时间戳转换工具类
 * author ：Mr.ZHU
 * date ：2019/1/22
 */
public class TimeFormartUtils {

    private static long currentTime;
    private static Date date;
    private static Date date1;
    private static Date date2;

    public static String getTimeDay() {
        currentTime = System.currentTimeMillis();
        String timeDay = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        return timeDay;
    }
    //一定要先调用上面的方法不然currentTime为空
    public static String getTime() {
        String timeNow = new SimpleDateFormat("HH:mm:ss").format(currentTime);
        return timeNow;
    }

    public static String getTime2() {
        currentTime = System.currentTimeMillis();
        String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
        return timeNow;
    }

    public static final SimpleDateFormat DEFAULT_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String currentTime() {
        Date date = new Date();
        return DEFAULT_FORMAT.format(date);
    }
    public static boolean getTimeNow(String time1, String time2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(System.currentTimeMillis());
        try {
            date = simpleDateFormat.parse(format);
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date.getTime()>=date1.getTime()&&date.getTime()<=date2.getTime()){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isTime(String format, String time1, String time2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(format);
            date1 = simpleDateFormat.parse(time1);
            date2 = simpleDateFormat.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date.getTime()>=date1.getTime()&&date.getTime()<=date2.getTime()){
            return true;
        }else {
            return false;
        }
    }
    /*获取星期几*/
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}