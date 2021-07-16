package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.request.AbsentRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.AbsentService;
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

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AbsentController {

    private static final Logger logger = Logger.getLogger(AbsentController.class);

    @Autowired
    private AbsentService absentService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_ABSENT)
    public ResponseEntity<?> searchAbsentDetail(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                @RequestParam(value = "identifyCard", required = false, defaultValue = "") String identifyCard,
                                                @RequestParam(value = "absentType", required = false, defaultValue = "-1") Long absentType) {
        logger.debug("searchAbsentDetail request : " + name + " - " + identifyCard + " - " + absentType);
        Integer pageSize = 5;
        ApiResponse apiResponse = absentService.absentList(name, identifyCard, absentType, pageNo, pageSize);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchAbsentDetail response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_EXPORT_SEARCH_ABSENT)
    public void exportAbsentDetailList(HttpServletResponse httpServletResponse,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "identifyCard", required = false) String identifyCard,
                                       @RequestParam(value = "absentType", required = false) Long absentType) {
        logger.debug("exportAbsentDetailList request : " + name + " - " + identifyCard + " - " + absentType);
        absentService.exportAbsentDetailList(httpServletResponse, name, identifyCard, absentType);
    }

    @PostMapping(value = Constants.UrlPath.URL_API_INSERT_ABSENT)
    public ResponseEntity<?> addAbsent(@RequestBody AbsentRequest request) {
        logger.debug("addAbsent request : " + new Gson().toJson(request));
        absentService.addAbsentDetail(request);
        ResponseEntity<String> response = new ResponseEntity("Add absent success", HttpStatus.CREATED);
        logger.debug("addAbsent response : " + new Gson().toJson(response));
        return response;
    }
}
