package com.ams.building.server.service;

import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;

public interface NotificationService {

    ApiResponse searchNotification(Integer page, Integer size, String name);

    void addNotification(NotificationRequest request);

}
