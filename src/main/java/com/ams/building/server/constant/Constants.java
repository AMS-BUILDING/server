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

        public static final String URL_API_SEARCH_ABSENT = "/admin/absent/search";
        public static final String URL_API_EXPORT_SEARCH_ABSENT = "/admin/absent/export";
        public static final String URL_API_INSERT_ABSENT = "/landlord/absent/add";
        public static final String URL_API_ADD_ACCOUNT = "/admin/account/add";
        public static final String URL_API_UPDATE_ACCOUNT = "/admin/account/update";
        public static final String URL_API_GET_ACCOUNT = "/admin/account/get-one";
        public static final String URL_API_FIND_ACCOUNT = "/admin/list/account";
        public static final String URL_API_INSERT_FEEDBACK = "/landlord/feedback/add";
        public static final String URL_API_SEARCH_FEEDBACK = "/admin/feedback/search";
        public static final String URL_API_SEARCH_POSITION = "/manager-service/position/search";
        public static final String URL_API_SEARCH_EMPLOYEE = "/manager-service/employee/search";
        public static final String URL_API_GET_ONE_EMPLOYEE = "/manager-service/employee/get-one";
        public static final String URL_API_REMOVE_EMPLOYEE = "/admin/employee/remove";
        public static final String URL_API_UPDATE_EMPLOYEE = "/admin/employee/update";
        public static final String URL_API_ADD_EMPLOYEE = "/admin/employee/add";
        public static final String URL_API_EXPORT_SEARCH_EMPLOYEE = "/manager-service/employee/export";
        public static final String URL_API_LIST_SERVICE = "/manager-service/service/list";
        public static final String URL_API_SEARCH_SERVICE = "/manager-service/service/search";
        public static final String URL_API_DETAIL_SERVICE = "/manager-service/detail-service/get-one";
        public static final String URL_API_SEARCH_NOTIFICATION = "/tenant/notification/search";
        public static final String URL_API_ADD_NOTIFICATION = "/admin/notification/add";
        public static final String URL_API_SEARCH_REQUEST_SERVICE = "/manager-service/request-service/search";
        public static final String URL_API_UPDATE_REQUEST_SERVICE = "/manager-service/request-service/update";
        public static final String URL_API_GET_ONE_REQUEST_SERVICE = "/manager-service/request-service/get-one";
        public static final String URL_API_LIST_STATUS_REQUEST_SERVICE = "/manager-service/request-service/list";
        public static final String URL_API_SEARCH_VEHICLE_CARD = "/manager-service/vehicle-card/search";
        public static final String URL_API_DETAIL_VEHICLE_CARD = "/manager-service/vehicle-card/get-one";
        public static final String URL_API_UPDATE_VEHICLE_CARD = "/manager-service/vehicle-card/update";
        public static final String URL_API_REMOVE_VEHICLE_CARD = "/manager-service/vehicle-card/remove";
        public static final String URL_API_LIST_STATUS_VEHICLE_CARD = "/manager-service/vehicle-card/list";
        public static final String URL_API_SEARCH_RESIDENT_CARD = "/admin/resident-card/search";
        public static final String URL_API_UPDATE_RESIDENT_CARD = "/admin/resident-card/update";
        public static final String URL_API_REMOVE_RESIDENT_CARD = "/admin/resident-card/remove";
        public static final String URL_API_ADD_RESIDENT_CARD = "/admin/resident-card/add";
        public static final String URL_API_LOGIN = "/login";
        public static final String URL_API_PROFILE = "/member/account/profile";
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

    public class ResidentCard {
        private ResidentCard() {
        }

        public static final String PRICE = "50000";
    }

    public class StatusResidentCard {
        private StatusResidentCard() {
        }

        public static final String SUCCESS = "Thành công";
    }
}
