package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ServiceResponse;
import com.ams.building.server.service.SubServiceService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ServiceController {

    private static final Logger logger = Logger.getLogger(ServiceController.class);

    @Autowired
    private SubServiceService serviceService;

    @GetMapping(value = Constants.UrlPath.URL_API_LIST_SERVICE)
    public ResponseEntity<?> serviceList() {
        List<ServiceResponse> serviceList = serviceService.serviceList();
        ResponseEntity<List<ServiceResponse>> response = new ResponseEntity<>(serviceList, HttpStatus.OK);
        logger.debug("serviceList response : " + new Gson().toJson(response));
        return response;
    }

}
