package com.ams.building.server.controller.app;

import com.google.gson.Gson;
import com.quan_ly_toa_nha.fpt.constant.Constants;
import com.quan_ly_toa_nha.fpt.response.NotificationAppResponse;
import com.quan_ly_toa_nha.fpt.service.NotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<NotificationAppResponse> notificationAppControllers = notificationService.listNotificationApp();
        ResponseEntity<List<NotificationAppResponse>> response = new ResponseEntity<>(notificationAppControllers, HttpStatus.CREATED);
        logger.debug("listNotificationGeneral response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_UDPATE_NOTIFICATION + "/{id}")
    public ResponseEntity<?> updateStatusRead(@PathVariable("id") Long id) {
        notificationService.updateStatus(id);
        ResponseEntity<String> response = new ResponseEntity<>("Update Status Read success", HttpStatus.CREATED);
        logger.debug("updateStatusRead response : " + new Gson().toJson(response));
        return response;
    }
}
