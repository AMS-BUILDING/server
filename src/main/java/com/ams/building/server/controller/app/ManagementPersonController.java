package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ManagerPersonResponse;
import com.ams.building.server.service.ManagementPersonService;
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
public class ManagementPersonController {

    private static final Logger logger = Logger.getLogger(ManagementPersonController.class);

    @Autowired
    private ManagementPersonService managementPersonService;

    @GetMapping(value = Constants.UrlPath.URL_API_MANAGEMENT_PERSON_APP)
    public ResponseEntity<?> managementList() {
        List<ManagerPersonResponse> managerPersonResponseList = managementPersonService.managementPersonServices();
        ResponseEntity<List<ManagerPersonResponse>> response = new ResponseEntity(managerPersonResponseList, HttpStatus.OK);
        logger.debug("managementList response: " + new Gson().toJson(response));
        return response;
    }

}
