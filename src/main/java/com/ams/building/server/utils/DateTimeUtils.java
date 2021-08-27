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

    private static final Logger logger = Logger.getLogger(DateTimeUtils.class);

    public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String MM_YYYY = "MM/yyyy";
    public static String HH_MM = "HH:mm";
    public static String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";

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
