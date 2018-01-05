package org.ps.uitl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式转换工具类
 */
public class DateUtils {
    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * long转字符串类型日期
     * @param time    long类型日期
     * @param format  日期格式
     * @return
     */
    public static String longToString(long time, String format){
        Date date = new Date(time);
        return new SimpleDateFormat(format).format(date);
    }
    public static void main(String[] args){
        //System.out.println(longToString(1514950903209L, "yyyy-MM-dd HH:mm:ss"));
    }
}
