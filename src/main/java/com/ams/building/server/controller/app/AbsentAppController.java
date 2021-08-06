package com.ams.building.server.controller.app;

import com.google.gson.Gson;
import com.quan_ly_toa_nha.fpt.constant.Constants;
import com.quan_ly_toa_nha.fpt.request.AbsentRequest;
import com.quan_ly_toa_nha.fpt.service.AbsentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AbsentAppController {

    private static final Logger logger = Logger.getLogger(AbsentAppController.class);

    @Autowired
    private AbsentService absentService;

    @PostMapping(value = Constants.UrlPath.URL_API_INSERT_ABSENT)
    public ResponseEntity<?> addAbsent(@RequestBody AbsentRequest request) {
        logger.debug("addAbsent request : " + new Gson().toJson(request));
        absentService.addAbsentDetail(request);
        ResponseEntity<String> response = new ResponseEntity("Add absent success", HttpStatus.CREATED);
        logger.debug("addAbsent response : " + new Gson().toJson(response));
        return response;
    }

}
