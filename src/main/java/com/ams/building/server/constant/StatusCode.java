package com.ams.building.server.constant;


import com.ams.building.server.utils.PropertiesReader;

public enum StatusCode {

    SUCCESS(0, "Success"),
    ERROR_UNKNOWN(1, "Error Unknown"),
    ABSENT_DETAIL_NOT_EXIST(401, PropertiesReader.getProperty(PropertyKeys.ABSENT_DETAIL_NOT_EXIST)),
    APARTMENT_NOT_EXIST(402, PropertiesReader.getProperty(PropertyKeys.APARTMENT_NOT_EXIST)),
    ROOM_NUMBER_NOT_EXIST(403, PropertiesReader.getProperty(PropertyKeys.ROOM_NUMBER_NOT_EXIST)),
    FLOOR_BLOCK_NOT_EXIST(404, PropertiesReader.getProperty(PropertyKeys.FLOOR_BLOCK_NOT_EXIST)),
    BLOCK_NOT_EXIST(405, PropertiesReader.getProperty(PropertyKeys.BLOCK_NOT_EXIST)),
    ABSENT_TYPE_NOT_EXIST(406, PropertiesReader.getProperty(PropertyKeys.ABSENT_TYPE_NOT_EXIST)),
    DATA_EMPTY(407, PropertiesReader.getProperty(PropertyKeys.DATA_EMPTY)),
    IDENTIFY_CARD_DUILCATE(408, PropertiesReader.getProperty(PropertyKeys.IDENTIFY_CARD_DUILCATE)),
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
    RESIDENT_CARD_NOT_EXIST(419, PropertiesReader.getProperty(PropertyKeys.RESIDENT_CARD_NOT_EXIST));

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
