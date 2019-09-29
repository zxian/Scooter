package com.xian.scooter.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeUtils {

    /**
     * 将毫秒转换为mm:ss
     *
     * @param duration
     * @return
     */
    public static String timeParse(long duration) {
        String time = "";

        long minute = duration / 60000;
        long seconds = duration % 60000;

        long second = Math.round((float) seconds / 1000);

        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";

        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

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
     * 获取当月的最初的毫秒值
     *
     * @param year  年
     * @param month 月 Calendar类中常用的方法；month参数（月参数）较其他参数特殊，0表示1月
     * @return 时间戳
     */
    public static long getMonthFisrtTime(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月的最后的毫秒值
     *
     * @param year  年
     * @param month 月 Calendar类中常用的方法；month参数（月参数）较其他参数特殊，0表示1月
     * @return 时间戳
     */
    public static long getMonthLastTime(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        if (month < 12) {
            calendar.set(year, month, 1, 0, 0, 0);
        } else {
            calendar.set(year + 1, 0, 1, 0, 0, 0);
        }
        return calendar.getTimeInMillis() - 1;
    }

    /**
     * 获取近一周的开始的毫秒值
     *
     * @return 时间戳
     */
    public static long getWeekNearTime() {
        Calendar calendar = Calendar.getInstance();
        long timeDay = 1000 * 60 * 60 * 24;
        return calendar.getTimeInMillis() - timeDay * 7;
    }

    /**
     * 获取某段时间起后第几天的开始的毫秒值
     *
     * @param time 某天的时间戳
     * @param day  天数
     * @return 时间戳
     */
    public static long getFisrtSomedayTime(long time, int day) {
        long timeDay = 1000 * 60 * 60 * 24;
        return time + timeDay * day;
    }

    /**
     * 获取某段时间起后第几的结束的毫秒值
     *
     * @param time 某天的时间戳
     * @param day  天数
     * @return 时间戳
     */
    public static long getLastSomedayTime(long time, int day) {
        long timeDay = 1000 * 60 * 60 * 24;
        return time + timeDay * day - 1;
    }

    /**
     * 获取近一个月的开始的毫秒值
     *
     * @return 时间戳
     */
    public static long getMonthNearTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month == 0) {
            calendar.set(year - 1, 11, day, 0, 0, 0);
        } else {
            calendar.set(year, month - 1, day, 0, 0, 0);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取近三个月的开始的毫秒值
     *
     * @return 时间戳
     */
    public static long getMonthThreeNearTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 3) {
            calendar.set(year - 1, 12 - 3 + month, day, 0, 0, 0);
        } else {
            calendar.set(year, month - 3, day, 0, 0, 0);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取近半年的开始的毫秒值
     *
     * @return 时间戳
     */
    public static long getYearHalfNearTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 6) {
            calendar.set(year - 1, 12 - 6 + month, day, 0, 0, 0);
        } else {
            calendar.set(year, month - 6, day, 0, 0, 0);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取近一个年的开始的毫秒值
     *
     * @return 时间戳
     */
    public static long getYearNearTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year - 1, month, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取当日的最初的毫秒值
     *
     * @param year  年
     * @param month 月 Calendar类中常用的方法；month参数（月参数）较其他参数特殊，0表示1月
     * @param day   日期
     * @return 时间戳
     */
    public static long getDayFisrtTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取当日的最末的毫秒值
     *
     * @param year  年
     * @param month 月 Calendar类中常用的方法；month参数（月参数）较其他参数特殊，0表示1月
     * @param day   日期
     * @return 时间戳
     */
    public static long getDayLastTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day, 23, 59, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当日时间中星期几最初的毫秒值
     *
     * @param number 0 星期一； 1 星期二； 2 星期三。。。。
     * @return 时间戳
     */
    public static long getWeekFisrtTime(Calendar toDate, int number) {
        toDate.setFirstDayOfWeek(Calendar.MONDAY);
        toDate.setTime(new Date());

        toDate.set(Calendar.DAY_OF_WEEK, toDate.getFirstDayOfWeek() + number);//修改到当周一时间

        return getDayFisrtTime(toDate.get(Calendar.YEAR),
                toDate.get(Calendar.MONTH) + 1, toDate.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取当日时间中星期几最末的毫秒值
     *
     * @param number 0 星期一； 1 星期二； 2 星期三。。。。
     * @return 时间戳
     */
    public static long getWeekLastTime(Calendar toDate, int number) {
        toDate.setFirstDayOfWeek(Calendar.MONDAY);
        toDate.setTime(new Date());
        toDate.set(Calendar.DAY_OF_WEEK, toDate.getFirstDayOfWeek() + number);//修改到当周日时间

        return TimeUtils.getDayLastTime(toDate.get(Calendar.YEAR),
                toDate.get(Calendar.MONTH) + 1, toDate.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getCurrentDateWithHMS() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getCurrentDateWithYHM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getCurrentDateWithHM() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
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
     * 判断两个时间是否同一天
     *
     * @param firstTime  第一个时间
     * @param secondTime 第二个时间
     * @return 是否同一天
     */
    public static boolean isSameDay(Long firstTime, Long secondTime) {
        if (firstTime == null || secondTime == null)
            return false;
        if (stampToDate(firstTime).equals(stampToDate(secondTime)))
            return true;
        return false;
    }

    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(new Date());
    }

    /**
     * 是否过期
     *
     * @param endTime
     * @return
     */
    public static boolean isExpiration(String endTime) {
        if (getCurrentTimeWithT().compareTo(endTime) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }

    public static String getCurrentWeek() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    /**
     * 将时间转换为时间戳
     *
     * @param dateString 时间
     * @return
     */
    public static long getStringToDate(String dateString) {
        if (dateString == null) {
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将时间转换为时间戳
     *
     * @param dateString 时间
     * @return
     */
    public static long getStringToDateHM(String dateString) {
        if (dateString == null) {
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        if (s == null) {
            return "";
        }
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long lt) {
        String res = "";
        if (lt != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        }
        return res;
    }

    public static String stampToDate2(long lt) {
        String res = "";
        if (lt != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        }
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToYearMonth(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDateWithHm(String s) {
        if (s == null) {
            return "0";
        }
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDateWithHms(String s) {
        if (s == null) {
            return "0";
        }
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDateWithHms(long lt) {
        String res;
        if (lt != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        } else {
            res = "";
        }
        return res;
    }

    public static String stampToDateWithHm2(long lt) {
        String res;
        if (lt != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        } else {
            res = "";
        }
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDateWithHm(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToMonthDayWithHm(String s) {
        if (s == null) {
            return "0";
        }
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToMonthDayWithHm(Long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：MM-dd HH:mm
     *
     * @param s
     * @return
     */
    public static String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：MM-dd HH:mm
     *
     * @param lt
     * @return
     */
    public static String dateToStamp(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：MM-dd
     *
     * @param lt
     * @return
     */
    public static String dateToHourMinute(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：MM-dd
     *
     * @param lt
     * @return
     */
    public static String dateToMonthDay(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：dd
     *
     * @param lt 时间戳
     * @return 日
     */
    public static String getDateforDay(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：MM
     *
     * @param lt 时间戳
     * @return 月
     */
    public static String getDateforMonth(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间：yyyy
     *
     * @param lt 时间戳
     * @return 年
     */
    public static String getDateforYear(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
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

}
