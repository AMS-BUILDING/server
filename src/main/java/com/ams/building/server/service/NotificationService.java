package com.ams.building.server.service;

import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.NotificationAppResponse;

import java.util.List;

public interface NotificationService {

    ApiResponse searchNotification(String title, Integer page, Integer size);

    void addNotification(NotificationRequest request);

    List<NotificationAppResponse> listNotificationAppGeneral();

    void updateStatus(Long notificationId);

    List<NotificationAppResponse> listNotificationAppPrivate(Long accountId);

}
