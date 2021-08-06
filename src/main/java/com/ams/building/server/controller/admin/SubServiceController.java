package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.DetailSubServiceResponse;
import com.ams.building.server.service.SubServiceService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class SubServiceController {

    private static final Logger logger = Logger.getLogger(SubServiceController.class);

    @Autowired
    private SubServiceService subServiceService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_SERVICE)
    public ResponseEntity<?> searchSubServiceByServiceNameAndSubServiceName(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                                            @RequestParam(value = "subService", required = false, defaultValue = "") String subServiceName,
                                                                            @RequestParam(value = "serviceId", required = false, defaultValue = "-1") Long serviceId) {

        logger.debug("searchSubServiceByServiceNameAndSubServiceName request : " + subServiceName + " - " + serviceId);
        Integer pageSize = 5;
        ApiResponse apiResponse = subServiceService.searchService(pageNo, pageSize, serviceId, subServiceName);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchSubServiceByServiceNameAndSubServiceName response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DETAIL_SERVICE + "/{id}")
    public ResponseEntity<?> detailReasonSubService(@PathVariable(name = "id") Long subServiceId) {

        logger.debug("detailReasonSubService request : " + subServiceId);
        List<DetailSubServiceResponse> responses = subServiceService.reasonDetailServiceBySubServiceId(subServiceId);
        ResponseEntity<List<DetailSubServiceResponse>> response = new ResponseEntity<>(responses, HttpStatus.OK);
        logger.debug("detailReasonSubService response : " + new Gson().toJson(response));
        return response;
    }

}
