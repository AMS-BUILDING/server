package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.NotificationService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification/")
@CrossOrigin(origins = "*", maxAge = -1)
public class NotificationController {

    private static final Logger logger = Logger.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_NOTIFICATION)
    public ResponseEntity<?> searchNotificationByTitle(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                       @RequestParam(value = "title", required = false, defaultValue = "") String title) {
        logger.debug("searchNotificationByTitle request : " + title);
        Integer pageSize = 5;
        ApiResponse apiResponse = notificationService.searchNotification(pageNo, pageSize, title);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchNotificationByTitle  response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_NOTIFICATION)
    public ResponseEntity<?> addNotification(@RequestBody NotificationRequest request) {
        logger.debug("addNotification request : " + new Gson().toJson(request));
        notificationService.addNotification(request);
        ResponseEntity<String> response = new ResponseEntity<>("Add Notification Success", HttpStatus.CREATED);
        logger.debug("addNotification  response : " + new Gson().toJson(response));
        return response;
    }
}
