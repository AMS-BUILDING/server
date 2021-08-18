package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Notification;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
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

@Transactional
@Service
public class NotificationSeviceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private ApartmentBillingDAO apartmentBillingDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Override
    public ApiResponse searchNotification(String title, Integer page, Integer size) {
        List<NotificationResponse> notificationDTOList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notifications = notificationDAO.searchNotificationByTitle(title, pageable);
        notifications.forEach(s -> notificationDTOList.add(convert(s)));
        Long totalElements = notifications.getTotalElements();
        ApiResponse apiResponse = ApiResponse.builder().data(notificationDTOList).totalElement(totalElements).build();
        return apiResponse;
    }

    @Override
    public void addNotification(NotificationRequest request) {
        if (Objects.isNull(request))
            throw new RestApiException(StatusCode.DATA_EMPTY);
        if (StringUtils.isEmpty(request.getDescription())) {
            throw new RestApiException(StatusCode.DESCRIPTION_EMPTY);
        }
        if (StringUtils.isEmpty(request.getTitle())) {
            throw new RestApiException(StatusCode.TITLE_EMPTY);
        }
        Notification notification = new Notification();
        notification.setDescription(request.getDescription());
        notification.setTitle(request.getTitle());
        notification.setIsRead(false);
        notificationDAO.save(notification);
    }

    @Override
    public List<NotificationAppResponse> listNotificationAppGeneral() {
        List<NotificationAppResponse> notificationDTOList = new ArrayList<>();
        List<Notification> notifications = notificationDAO.listNotification();
        notifications.forEach(s -> notificationDTOList.add(convertToNotificationApp(s)));
        return notificationDTOList;
    }

    @Override
    public void updateStatus(Long notificationId) {
        if (StringUtils.isEmpty(notificationId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Notification notification = notificationDAO.getById(notificationId);
        if (Objects.isNull(notification)) {
            throw new RestApiException(StatusCode.NOTIFICATION_NOT_EXIST);
        }
        notification.setIsRead(true);
        notificationDAO.save(notification);
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
            String monthNext = String.valueOf(Integer.valueOf(month) + 1);
            if (monthNext.length() == 1) {
                monthNext = "0" + monthNext;
            }
            if (month.equals("12")) {
                monthNext = "01";
            }
            NotificationAppResponse notificationAppResponse = NotificationAppResponse.builder().build();
            notificationAppResponse.setTitle("Thông báo phí tháng " + apartmentBilling.getBillingMonth() + " của căn hộ " + apartment.getRoomNumber().getRoomName());
            String mess = "Tổng số tiền bạn phải thanh toán là : " + HelperUtils.formatDoubleNUmber(apartmentBilling.getTotalPrice() + fee);
            mess += ". Bạn phải thanh toán trước ngày 10/" + monthNext;
            notificationAppResponse.setDescription(mess);
            responses.add(notificationAppResponse);
        }
        return responses;
    }

    private NotificationResponse convert(Notification notification) {
        NotificationResponse response = NotificationResponse.builder().build();
        response.setDescription(notification.getDescription());
        response.setTitle(notification.getTitle());
        return response;
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
