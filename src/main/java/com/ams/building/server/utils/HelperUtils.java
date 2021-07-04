package com.ams.building.server.utils;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;

public class HelperUtils {

    private static final Logger logger = Logger.getLogger(HelperUtils.class);

    public static String formatDoubleNUmber(Double d) {
        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(d);
    }

}
