package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Notification;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.NotificationResponse;
import com.ams.building.server.service.NotificationService;
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

@Service
public class NotificationSeviceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

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
        if (Objects.isNull(request) || StringUtils.isEmpty(request.getDescription()) || StringUtils.isEmpty(request.getTitle()))
            throw new RestApiException(StatusCode.DATA_EMPTY);
        Notification notification = new Notification();
        notification.setDescription(request.getDescription());
        notification.setTitle(request.getTitle());
        notificationDAO.save(notification);
    }

    private NotificationResponse convert(Notification notification) {
        NotificationResponse response = NotificationResponse.builder().build();
        response.setDescription(notification.getDescription());
        response.setTitle(notification.getTitle());
        return response;
    }
}
