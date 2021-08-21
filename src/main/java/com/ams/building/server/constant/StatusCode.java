package com.ams.building.server.constant;

import com.ams.building.server.utils.PropertiesReader;

public enum StatusCode {

    SUCCESS(0, "Success"),
    ERROR_UNKNOWN(1, "Error Unknown"),
    ABSENT_DETAIL_NOT_EXIST(401, PropertiesReader.getProperty(PropertyKeys.ABSENT_DETAIL_NOT_EXIST)),
    APARTMENT_NOT_EXIST(402, PropertiesReader.getProperty(PropertyKeys.APARTMENT_NOT_EXIST)),
    FLOOR_BLOCK_NOT_EXIST(404, PropertiesReader.getProperty(PropertyKeys.FLOOR_BLOCK_NOT_EXIST)),
    BLOCK_NOT_EXIST(405, PropertiesReader.getProperty(PropertyKeys.BLOCK_NOT_EXIST)),
    ABSENT_TYPE_NOT_EXIST(406, PropertiesReader.getProperty(PropertyKeys.ABSENT_TYPE_NOT_EXIST)),
    DATA_EMPTY(407, PropertiesReader.getProperty(PropertyKeys.DATA_EMPTY)),
    IDENTIFY_CARD_DUPLICATE(408, PropertiesReader.getProperty(PropertyKeys.IDENTIFY_CARD_DUPLICATE)),
    IDENTIFY_CARD_NOT_RIGHT(409, PropertiesReader.getProperty(PropertyKeys.IDENTIFY_CARD_NOT_RIGHT)),
    FEEDBACK_EMPTY(410, PropertiesReader.getProperty(PropertyKeys.FEEDBACK_EMPTY)),
    ACCOUNT_NOT_EXIST(411, PropertiesReader.getProperty(PropertyKeys.ACCOUNT_NOT_EXIST)),
    EMAIL_NOT_RIGHT_FORMAT(412, PropertiesReader.getProperty(PropertyKeys.EMAIL_NOT_RIGHT_FORMAT)),
    POSITION_NOT_EXIST(413, PropertiesReader.getProperty(PropertyKeys.POSITION_NOT_EXIST)),
    PHONE_NUMBER_NOT_RIGHT_FORMAT(414, PropertiesReader.getProperty(PropertyKeys.PHONE_NUMBER_NOT_RIGHT_FORMAT)),
    SUB_SERVICE_NOT_EXIST(415, PropertiesReader.getProperty(PropertyKeys.SUB_SERVICE_NOT_EXIST)),
    REQUEST_SERVICE_NOT_EXIST(416, PropertiesReader.getProperty(PropertyKeys.REQUEST_SERVICE_NOT_EXIST)),
    DETAIL_SUB_SERVICE_NOT_EXIST(417, PropertiesReader.getProperty(PropertyKeys.DETAIL_SUB_SERVICE_NOT_EXIST)),
    VEHICLE_CARD_NOT_EXIST(418, PropertiesReader.getProperty(PropertyKeys.VEHICLE_CARD_NOT_EXIST)),
    RESIDENT_CARD_NOT_EXIST(419, PropertiesReader.getProperty(PropertyKeys.RESIDENT_CARD_NOT_EXIST)),
    ROOM_NUMBER_NOT_EXIST(420, PropertiesReader.getProperty(PropertyKeys.ROOM_NUMBER_NOT_EXIST)),
    LOGIN_FAIL(421, PropertiesReader.getProperty(PropertyKeys.LOGIN_FAIL)),
    PAGE_FORBIDDEN(422, PropertiesReader.getProperty(PropertyKeys.PAGE_FORBIDDEN)),
    NAME_EMPTY(423, PropertiesReader.getProperty(PropertyKeys.NAME_EMPTY)),
    DOB_EMPTY(424, PropertiesReader.getProperty(PropertyKeys.DOB_EMPTY)),
    HOME_TOWN_EMPTY(425, PropertiesReader.getProperty(PropertyKeys.HOME_TOWN_EMPTY)),
    REASON_EMPTY(426, PropertiesReader.getProperty(PropertyKeys.REASON_EMPTY)),
    START_DATE_EMPTY(427, PropertiesReader.getProperty(PropertyKeys.START_DATE_EMPTY)),
    END_DATE_EMPTY(428, PropertiesReader.getProperty(PropertyKeys.END_DATE_EMPTY)),
    EMAIL_EMPTY(429, PropertiesReader.getProperty(PropertyKeys.EMAIL_EMPTY)),
    PASSWORD_EMPTY(430, PropertiesReader.getProperty(PropertyKeys.PASSWORD_EMPTY)),
    PHONE_EMPTY(431, PropertiesReader.getProperty(PropertyKeys.PHONE_EMPTY)),
    IDENTIFY_CARD_EMPTY(432, PropertiesReader.getProperty(PropertyKeys.IDENTIFY_CARD_EMPTY)),
    CURRENT_ADDRESS_EMPTY(433, PropertiesReader.getProperty(PropertyKeys.CURRENT_ADDRESS_EMPTY)),
    PASSWORD_NOT_MATCH(434, PropertiesReader.getProperty(PropertyKeys.PASSWORD_NOT_MATCH)),
    ACCOUNT_REGISTER(435, PropertiesReader.getProperty(PropertyKeys.ACCOUNT_REGISTER)),
    DESCRIPTION_EMPTY(436, PropertiesReader.getProperty(PropertyKeys.DESCRIPTION_EMPTY)),
    TITLE_EMPTY(437, PropertiesReader.getProperty(PropertyKeys.TITLE_EMPTY)),
    PASSWORD_USED(438, PropertiesReader.getProperty(PropertyKeys.PASSWORD_USED)),
    PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCH(439, PropertiesReader.getProperty(PropertyKeys.PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCH)),
    PASSWORD_NOT_RIGHT_FORMAT(440, PropertiesReader.getProperty(PropertyKeys.PASSWORD_NOT_RIGHT_FORMAT)),
    STAR_EMPTY(441, PropertiesReader.getProperty(PropertyKeys.STAR_EMPTY)),
    VEHICLE_NOT_EXIST(442, PropertiesReader.getProperty(PropertyKeys.VEHICLE_NOT_EXIST)),
    BUILDING_NOT_EXIST(443, PropertiesReader.getProperty(PropertyKeys.BUILDING_NOT_EXIST)),
    EMAIL_REGISTER_BEFORE(444, PropertiesReader.getProperty(PropertyKeys.EMAIL_REGISTER_BEFORE)),
    DUPLICATE_EMAIL_IN_LIST_RESIDENT(445, PropertiesReader.getProperty(PropertyKeys.DUPLICATE_EMAIL_IN_LIST_RESIDENT)),
    DUPLICATE_IDENTIFY_CARD_IN_LIST_RESIDENT(446, PropertiesReader.getProperty(PropertyKeys.DUPLICATE_IDENTIFY_CARD_IN_LIST_RESIDENT)),
    REQUEST_SERVICE_REGISTER_BEFORE(447, PropertiesReader.getProperty(PropertyKeys.REQUEST_SERVICE_REGISTER_BEFORE)),
    REASON_DETAIL_SUB_SERVICE_NOT_EXIST(448, PropertiesReader.getProperty(PropertyKeys.REASON_DETAIL_SUB_SERVICE_NOT_EXIST)),
    ACCOUNT_NOT_RIGHT_ROLE(449, PropertiesReader.getProperty(PropertyKeys.ACCOUNT_NOT_RIGHT_ROLE)),
    TYPE_APARTMENT_NOT_EXIST(450, PropertiesReader.getProperty(PropertyKeys.TYPE_APARTMENT_NOT_EXIST)),
    NOTIFICATION_NOT_EXIST(451, PropertiesReader.getProperty(PropertyKeys.NOTIFICATION_NOT_EXIST)),
    BILLING_MONTH_NOT_RIGHT(452, PropertiesReader.getProperty(PropertyKeys.BILLING_MONTH_NOT_RIGHT)),
    PASSWORD_NOT_RIGHT(453, PropertiesReader.getProperty(PropertyKeys.PASSWORD_NOT_RIGHT)),
    USER_NAME_EMPTY(454, PropertiesReader.getProperty(PropertyKeys.USER_NAME_EMPTY)),
    POSITION_EMPTY(455, PropertiesReader.getProperty(PropertyKeys.POSITION_EMPTY)),
    PHONE_REGISTER_BEFORE(456, PropertiesReader.getProperty(PropertyKeys.PHONE_REGISTER_BEFORE)),
    POSITION_MUST_BE_IN_HOME(457, PropertiesReader.getProperty(PropertyKeys.POSITION_MUST_BE_IN_HOME)),
    STAR_FROM_ONE_TO_FIVE(458, PropertiesReader.getProperty(PropertyKeys.STAR_FROM_ONE_TO_FIVE)),
    POSITION_NOT_RIGHT_WITH_EMPLOYEE(459, PropertiesReader.getProperty(PropertyKeys.POSITION_NOT_RIGHT_WITH_EMPLOYEE)),
    CODE_NOT_RIGHT(460, PropertiesReader.getProperty(PropertyKeys.CODE_NOT_RIGHT)),
    STATUS_NOT_EXIST(461, PropertiesReader.getProperty(PropertyKeys.STATUS_NOT_EXIST)),
    APARTMENT_BILLING_NOT_EXIST(462, PropertiesReader.getProperty(PropertyKeys.APARTMENT_BILLING_NOT_EXIST)),
    EMPLOYEE_NOT_WORKING(463, PropertiesReader.getProperty(PropertyKeys.EMPLOYEE_NOT_WORKING)),
    ACCOUNT_NOT_NEED_CHOOSE_POSITION(464, PropertiesReader.getProperty(PropertyKeys.ACCOUNT_NOT_NEED_CHOOSE_POSITION));

    private Integer status;

    private String message;

    StatusCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
