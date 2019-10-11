package com.xian.scooter.utils;

import android.text.TextUtils;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeUtils {

    /**
     * 获取当前时间
     *
     * @return String
     */
    public static long getCurrentTime() {
        Date date = new Date();
        return date.getTime();
    }


    /**
     * 获取当前时间
     *
     * @param formatStr 时间格式：yyyyMMddHHmmSSSS
     * @return 时间戳
     */
    public static String getCurrentTime(String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.CANADA);
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTimeWithT() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -8);// 24小时制
        date = cal.getTime();
        cal = null;
        return format.format(date);
    }

    /**
     * 将时间转换为时间戳
     *
     * @param dateString 时间
     * @param pattern 转换格式 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getStringToDate(String dateString,String pattern) {
        if (dateString == null) {
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将时间戳转换为时间
     * @param lt 时间戳
     * @param pattern 转换格式 如：yyyy-MM-dd HH:mm
     * @return
     */
    public static String stampToDate(long lt,String pattern) {
        String res = "";
        if (lt != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        }
        return res;
    }

    /**
     * 将年月日格式：20190101转化成2019-01-01这种格式
     *
     * @param date 20190101
     * @return 2019-01-01
     */
    public static String getDateFormat(String date) {
        String dateFormat = null;
        if (!TextUtils.isEmpty(date)) {
            StringBuilder sb = new StringBuilder(date);
            sb.insert(4, "-").insert(7, "-");
            dateFormat = sb.toString();
        }
        return dateFormat;
    }

    /**
     * 将date格式的时间转换成时间格式
     *
     * @param oldDateStr date格式字符串
     * @param dateType   时间格式： 0，yyyy-MM-dd HH:mm:ss  1，yyyy-MM-dd 2 ,HH:mm
     * @return 时间格式字符串
     */
    public static String dealDateFormat(String oldDateStr, int dateType) {
        if (TextUtils.isEmpty(oldDateStr))
            return "";
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            Date date = df.parse(oldDateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            SimpleDateFormat df2;
            switch (dateType) {
                case 0:
                    df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    break;
                case 1:
                    df2 = new SimpleDateFormat("yyyy-MM-dd");
                    break;
                case 2:
                    df2 = new SimpleDateFormat("HH:mm");
                    break;
                default:
                    df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    break;
            }

            return df2.format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static Date getNetTime(){
        String webUrl = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
        try {
            URL url = new URL(webUrl);
            URLConnection uc = url.openConnection();
            uc.setReadTimeout(5000);
            uc.setConnectTimeout(5000);
            uc.connect();
            long correctTime = uc.getDate();
            Date date = new Date(correctTime);
            return date;
        } catch (Exception e) {
            return new Date();
        }
    }
}
