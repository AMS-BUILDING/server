package com.ams.building.server.constant;

public class Constants {

    private Constants() {
    }

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String TEXT_CSV = "text/csv";

    public static final String HEADER_KEY = "Content-Disposition";

    public class UrlPath {

        private UrlPath() {
        }

        public static final String URL_API_SEARCH_ABSENT = "search";
        public static final String URL_API_EXPORT_SEARCH_ABSENT = "export";
        public static final String URL_API_INSERT_ABSENT = "add";
        public static final String URL_API_ADD_ACCOUNT = "add";
        public static final String URL_API_UPDATE_ACCOUNT = "update";
        public static final String URL_API_GET_ACCOUNT = "get-one";
        public static final String URL_API_FIND_ACCOUNT = "find";
        public static final String URL_API_INSERT_FEEDBACK = "add";
        public static final String URL_API_SEARCH_FEEDBACK = "search";
        public static final String URL_API_SEARCH_POSITION = "search";
        public static final String URL_API_SEARCH_EMPLOYEE = "search";
        public static final String URL_API_GET_ONE_EMPLOYEE = "get-one";
        public static final String URL_API_REMOVE_EMPLOYEE = "remove";
        public static final String URL_API_UPDATE_EMPLOYEE = "update";
        public static final String URL_API_ADD_EMPLOYEE = "add";
        public static final String URL_API_EXPORT_SEARCH_EMPLOYEE = "export";
        public static final String URL_API_LIST_SERVICE = "list";
        public static final String URL_API_SEARCH_SERVICE = "search";
        public static final String URL_API_DETAIL_SERVICE = "get-one";
        public static final String URL_API_SEARCH_NOTIFICATION = "search";
        public static final String URL_API_ADD_NOTIFICATION = "add";
        public static final String URL_API_SEARCH_REQUEST_SERVICE = "search";
        public static final String URL_API_UPDATE_REQUEST_SERVICE = "update";
        public static final String URL_API_GET_ONE_REQUEST_SERVICE = "get-one";
        public static final String URL_API_LIST_STATUS_REQUEST_SERVICE = "list";
        public static final String URL_API_SEARCH_VEHICLE_CARD = "search";
        public static final String URL_API_DETAIL_VEHICLE_CARD = "get-one";
        public static final String URL_API_UPDATE_VEHICLE_CARD = "update";
        public static final String URL_API_REMOVE_VEHICLE_CARD = "remove";
    }

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String PROPERTIES_VALIDATION = "validation.properties";
    }

    public class AccountGender {
        private AccountGender() {

        }

        public static final String GENDER_MALE = "nam";
        public static final String GENDER_FEMALE = "nữ";
    }
}
