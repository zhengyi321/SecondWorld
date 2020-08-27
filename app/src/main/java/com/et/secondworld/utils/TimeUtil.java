package com.et.secondworld.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/25
 **/
public class TimeUtil {
    /**
     * 根据传入的原始日期，获取截取后的消息日期。
     * 日期有三种显示形式，今天、昨天、昨天之前。
     * <p>
     * 例：今天是4月11号：
     * 若传入原始日期dateOriginal是"2018-09-03 12:32:40"，则返回"2018-09-03 12:32"，
     * 若传入原始日期dateOriginal是"2019-04-10 12:32:40"，则返回"昨天12:32"，
     * 若传入原始日期dateOriginal是"2019-04-11 12:32:40"，则返回"12:32"。
     *
     * @param dateOriginal 原始日期
     * @return 返回截取后的日期
     */
    public static String getMessageDate(String dateOriginal) {
        long original = 0;
        long oneDay = 1000 * 60 * 60 * 24;
        try {
            //拿到传过来的日期的毫秒值
            original = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateOriginal).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //设置一个日期表示今天零点
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        //与今天零点比较
        if (original >= today.getTimeInMillis()) {
            //今天
            return dateOriginal.substring(11, 16);
        } else {
            //与昨天零点比较
            if (original >= today.getTimeInMillis() - oneDay) {
                //昨天
                return "昨天" + dateOriginal.substring(11, 16);
            }
        }
        //都不是返回原始数据，去掉秒数
        return dateOriginal.substring(0, 16);
    }

    public static Date getCurrentTimeDate()
    {


        int year=0;
        int month=0;
        int day=0;
        int hour=0;
        int min =0;
        int ss = 0;
        Calendar c=Calendar.getInstance();//获得系统当前日期
		/*TimeZone tztz = TimeZone.getTimeZone("GMT");
		c.setTimeZone(tztz);*/
//		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
//
//		TimeZone.setDefault(timeZone);
//		c.setTimeZone(timeZone);
        year=c.get(Calendar.YEAR)-1900;
        month=c.get(Calendar.MONTH);//系统日期从0开始算起
        day=c.get(Calendar.DAY_OF_MONTH);
        hour=c.get(Calendar.HOUR_OF_DAY);
        min=c.get(Calendar.MINUTE);
        ss=c.get(Calendar.SECOND);
        Date date = new Date(year,month,day,hour,min,ss);
        return date;
    }
}
