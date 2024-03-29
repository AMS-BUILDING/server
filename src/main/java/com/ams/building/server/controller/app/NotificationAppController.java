package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.NotificationAppResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.NotificationService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class NotificationAppController {

    private static final Logger logger = Logger.getLogger(NotificationAppController.class);

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = Constants.UrlPath.URL_API_LIST_NOTIFICATION)
    public ResponseEntity<?> listNotificationGeneral() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        List<NotificationAppResponse> notificationAppControllers = notificationService.listNotificationAppGeneral(accountId);
        ResponseEntity<List<NotificationAppResponse>> response = new ResponseEntity<>(notificationAppControllers, HttpStatus.OK);
        logger.debug("listNotificationGeneral response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_UDPATE_NOTIFICATION)
    public ResponseEntity<?> updateStatusRead() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        notificationService.updateStatus(accountId);
        ResponseEntity<String> response = new ResponseEntity<>("Update Status Read success", HttpStatus.CREATED);
        logger.debug("updateStatusRead response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_LIST_NOTIFICATION_PRIVATE)
    public ResponseEntity<?> listNotificationPrivate() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        List<NotificationAppResponse> notificationAppControllers = notificationService.listNotificationAppPrivate(accountId);
        ResponseEntity<List<NotificationAppResponse>> response = new ResponseEntity<>(notificationAppControllers, HttpStatus.OK);
        logger.debug("listNotificationPrivate response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_LIST_NOTIFICATION_NUMBER)
    public ResponseEntity<?> numberNotificationNew() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        Integer numberNotification = notificationService.showNotificationNotRead(accountId);
        ResponseEntity<Integer> response = new ResponseEntity<>(numberNotification, HttpStatus.OK);
        logger.debug("numberNotificationNew response : " + new Gson().toJson(response));
        return response;
    }

}
