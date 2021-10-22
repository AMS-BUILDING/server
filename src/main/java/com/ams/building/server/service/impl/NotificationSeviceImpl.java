package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Notification;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.NotificationAppResponse;
import com.ams.building.server.response.NotificationResponse;
import com.ams.building.server.service.NotificationService;
import com.ams.building.server.utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.HH_MM;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;

@Service
public class NotificationSeviceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private ApartmentBillingDAO apartmentBillingDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public ApiResponse searchNotification(String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NotificationResponse> notifications = notificationDAO.searchNotificationByTitle(title, pageable);
        Long totalElements = notifications.getTotalElements();
        List<NotificationResponse> responses = new ArrayList<>();
        for (int i = notifications.getContent().size() - 1; i >= 0; i--) {
            responses.add(notifications.getContent().get(i));
        }
        ApiResponse apiResponse = ApiResponse.builder().data(responses).totalElement(totalElements).build();
        return apiResponse;
    }

    @Transactional
    @Override
    public void addNotification(NotificationRequest request) {
        if (Objects.isNull(request) || (StringUtils.isEmpty(request.getDescription()) && StringUtils.isEmpty(request.getTitle())))
            throw new RestApiException(StatusCode.DATA_EMPTY);
        if (StringUtils.isEmpty(request.getDescription())) {
            throw new RestApiException(StatusCode.DESCRIPTION_EMPTY);
        }
        if (StringUtils.isEmpty(request.getTitle())) {
            throw new RestApiException(StatusCode.TITLE_EMPTY);
        }
        List<Account> accounts = accountDAO.getAccountByRoles();
        if (accounts.isEmpty()) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        List<Notification> dataInsert = new ArrayList<>();
        for (Account account : accounts) {
            Notification notification = new Notification();
            notification.setDescription(request.getDescription().trim());
            notification.setTitle(request.getTitle().trim());
            notification.setAccount(account);
            notification.setIsRead(false);
            dataInsert.add(notification);
        }
        notificationDAO.saveAllAndFlush(dataInsert);
    }

    @Override
    public List<NotificationAppResponse> listNotificationAppGeneral(Long accountId) {
        List<NotificationAppResponse> notificationDTOList = new ArrayList<>();
        List<Notification> notifications = notificationDAO.listNotification(accountId);
        notifications.forEach(s -> notificationDTOList.add(convertToNotificationApp(s)));
        return notificationDTOList;
    }

    @Override
    public void updateStatus(Long accountId) {
        if (Objects.isNull(accountId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(accountId);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        List<Notification> listNotificationNotRead = notificationDAO.listNotificationNotRead(accountId);
        List<ApartmentBilling> listBillingNotRead = apartmentBillingDAO.listApartmentBillingNotRead(accountId);

        for (Notification notification : listNotificationNotRead) {
            notification.setIsRead(true);
            notificationDAO.save(notification);
        }
        for (ApartmentBilling apartmentBilling : listBillingNotRead) {
            apartmentBilling.setIsRead(true);
            apartmentBillingDAO.save(apartmentBilling);
        }
    }

    @Override
    public List<NotificationAppResponse> listNotificationAppPrivate(Long accountId) {
        if (StringUtils.isEmpty(accountId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(accountId);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        List<ApartmentBilling> apartmentBillings = apartmentBillingDAO.detailApartmentBuildingByMonth(apartment.getId());
        String apartmentSquarMetter = apartment.getRoomNumber().getTypeApartment().getTypeName();
        Long squarMetter = Long.valueOf(apartmentSquarMetter);
        Long fee = Long.valueOf(Constants.GeneralSerivce.FEE_GENERAL_SERVICE) * squarMetter;
        List<NotificationAppResponse> responses = new ArrayList<>();
        for (ApartmentBilling apartmentBilling : apartmentBillings) {
            String month = apartmentBilling.getBillingMonth().split("/")[0];
            String year = apartmentBilling.getBillingMonth().split("/")[1];
            String monthNext = String.valueOf(Integer.valueOf(month) + 1);
            if (monthNext.length() == 1) {
                monthNext = "0" + monthNext;
            }
            if (month.equals("12")) {
                monthNext = "01";
                year = String.valueOf(Integer.valueOf(year) + 1);
            }
            Long feeTotal = apartmentBilling.getTotalPrice().longValue() + fee;
            NotificationAppResponse notificationAppResponse = NotificationAppResponse.builder().build();
            notificationAppResponse.setTitle("Thông báo phí căn hộ số " + apartment.getRoomNumber().getRoomName() + " tháng " + apartmentBilling.getBillingMonth() + " của căn hộ " + apartment.getRoomNumber().getRoomName());
            String mess = "Tổng số tiền quý cư dân cần hoàn thành thanh toán trong tháng là : " + HelperUtils.formatCurrentMoney(feeTotal);
            mess += ". Quý cư dân thanh toán trước ngày 10/" + monthNext + "/" + year;
            notificationAppResponse.setDescription(mess);
            notificationAppResponse.setTime("00:00");
            notificationAppResponse.setDate("01/" + monthNext + "/" + year);
            responses.add(notificationAppResponse);
        }
        return responses;
    }

    @Override
    public Integer showNotificationNotRead(Long accountId) {
        if (Objects.isNull(accountId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(accountId);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Integer totalNotificationGeneral = notificationDAO.countNotificationNotRead(accountId);
        Integer totalNotificationPrivate = apartmentBillingDAO.countNotificationNotReadPrivate(accountId);
        return totalNotificationGeneral + totalNotificationPrivate;
    }

    private NotificationAppResponse convertToNotificationApp(Notification notification) {
        NotificationAppResponse response = NotificationAppResponse.builder().build();
        response.setDescription(notification.getDescription());
        response.setTitle(notification.getTitle());
        response.setId(notification.getId());
        String date = convertDateToStringWithTimezone(notification.getCreatedDate(), DD_MM_YYYY, null);
        String time = convertDateToStringWithTimezone(notification.getCreatedDate(), HH_MM, null);
        response.setTime(time);
        response.setDate(date);
        response.setIsRead(notification.getIsRead());
        return response;
    }

}
