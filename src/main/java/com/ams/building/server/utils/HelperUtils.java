package com.ams.building.server.utils;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class HelperUtils {

    private static final Logger logger = Logger.getLogger(HelperUtils.class);

    public static String formatDoubleNUmber(Double d) {
        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(d);
    }

    public static String formatCurrentMoney(Long d) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(d);
    }

}
