package com.ams.building.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

    private static final String PHONE_NUMBER_PATTERN = "^0[0-9]{9}";
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final String IDENTIFY_CARD_PATTERN = "[0-9]{9}|[0-9]{12}";


    public static boolean isPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isIdentifyCard(String identifyCard) {
        Pattern pattern = Pattern.compile(IDENTIFY_CARD_PATTERN);
        Matcher matcher = pattern.matcher(identifyCard);
        return matcher.matches();
    }
}
