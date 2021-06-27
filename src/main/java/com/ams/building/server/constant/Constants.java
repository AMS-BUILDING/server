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
        public static final String URL_API_LIST_ACCOUNT = "list/account";
        public static final String URL_API_ADD_ACCOUNT = "account/add";
        public static final String URL_API_UPDATE_ACCOUNT = "account/update";
        public static final String URL_API_GET_ACCOUNT = "account";
    }

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String PROPERTIES_VALIDATION = "validation.properties";
    }
}
