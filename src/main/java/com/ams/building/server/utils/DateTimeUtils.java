package com.ams.building.server.utils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {

    public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String MM_YYYY = "MM/yyyy";
    public static String HH_MM = "HH:mm";

    private static final Logger logger = Logger.getLogger(DateTimeUtils.class);

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static long dateDiff(Date from, Date to) {
        return (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static String convertDateToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String convertDateToStringWithTimezone(Date date, String format, String timeZone) {
        if (date == null) return "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (!StringUtils.isEmpty(timeZone)) {
                sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
            }
            return sdf.format(date);
        } catch (Exception e) {
            logger.error("convertDateToStringWithTimezone error : ", e);
            return "";
        }
    }

    public static Date convertStringToDate(String date, String format) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            return fmt.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
