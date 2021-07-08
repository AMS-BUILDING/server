package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.service.RequestServiceService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/request-service/")
@CrossOrigin(origins = "*", maxAge = -1)
public class ServiceRequestController {

    private static final Logger logger = Logger.getLogger(ServiceRequestController.class);

    @Autowired
    private RequestServiceService requestServiceService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_REQUEST_SERVICE)
    public ResponseEntity<?> searchRequestService(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                  @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                  @RequestParam(value = "serviceName", required = false, defaultValue = "") String serviceName,
                                                  @RequestParam(value = "statusId", required = false, defaultValue = "-1") Long statusId) {

        logger.debug("searchRequestService request : " + name + " - " + serviceName + " - " + statusId);
        Integer pageSize = 5;
        ApiResponse apiResponse = requestServiceService.searchServiceRequest(name, serviceName, statusId, pageNo, pageSize);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchRequestService response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_GET_ONE_REQUEST_SERVICE + "/{id}")
    public ResponseEntity<?> getOneRequestServiceById(@PathVariable("id") Long requestId) {
        logger.debug("getOneRequestServiceById: request " + requestId);
        RequestServiceResponse requestServiceResponse = requestServiceService.getRequestServiceById(requestId);
        ResponseEntity<RequestServiceResponse> response = new ResponseEntity<>(requestServiceResponse, HttpStatus.OK);
        logger.debug("getOneRequestServiceById response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_ADD_REQUEST_SERVICE + "/{id}")
    public ResponseEntity<?> updateStatusRequestService(@PathVariable("id") Long requestId, @RequestBody Long statusId) {
        logger.debug("updateStatusRequestService: request " + requestId + "-" + statusId);
        requestServiceService.updateStatusRequest(statusId, requestId);
        ResponseEntity<String> response = new ResponseEntity<>("Update Success", HttpStatus.OK);
        logger.debug("updateStatusRequestService response: " + new Gson().toJson(response));
        return response;
    }
}