package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.NotificationBuilding;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.BuildingDAO;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.NotificationResponse;
import com.ams.building.server.service.NotificationService;
import org.apache.log4j.Logger;
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

@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private BuildingDAO buildingDAO;

    @Override
    public ApiResponse searchNotification(Integer page, Integer size, String name) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<NotificationBuilding> notificationBuildingPage = notificationDAO.searchNotificationByTitle(name, pageable);
        notificationBuildingPage.forEach(s -> notificationResponses.add(covertToNotificationResponse(s)));
        Integer totalPage = notificationBuildingPage.getTotalPages();
        ApiResponse response = ApiResponse.builder().data(notificationResponses).totalPage(totalPage).build();
        return response;
    }

    @Override
    public void addNotification(NotificationRequest request) {
        if (Objects.isNull(request) || StringUtils.isEmpty(request.getDescription()) || StringUtils.isEmpty(request.getTitle())) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        NotificationBuilding notificationBuilding = new NotificationBuilding();
        Building building = buildingDAO.getById(1L);
        notificationBuilding.setBuilding(building);
        notificationBuilding.setTitleNotification(request.getTitle());
        notificationBuilding.setDescription(request.getDescription());
        notificationDAO.save(notificationBuilding);
    }

    private NotificationResponse covertToNotificationResponse(NotificationBuilding notificationBuilding) {
        NotificationResponse response = NotificationResponse.builder().build();
        response.setDescription(notificationBuilding.getDescription());
        response.setTitle(notificationBuilding.getTitleNotification());
        return response;
    }
}
