package com.ams.building.server.service;

import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;

public interface NotificationService {

    ApiResponse searchNotification(String title, Integer page, Integer size);

    void addNotification(NotificationRequest request);

}
