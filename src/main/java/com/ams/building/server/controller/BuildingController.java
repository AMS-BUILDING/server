package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.BuildingResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.BuildingService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class BuildingController {

    private static final Logger logger = Logger.getLogger(BuildingController.class);

    @Autowired
    private BuildingService service;

    @GetMapping(Constants.UrlPath.URL_API_DETAIL_BUILDING_APP)
    public ResponseEntity<?> buildingDetail() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        logger.debug("buildingDetail : request " + currentUser.getId());
        BuildingResponse buildingResponse = service.detailBuilding(currentUser.getId());
        ResponseEntity<BuildingResponse> response = new ResponseEntity<>(buildingResponse, HttpStatus.OK);
        logger.debug("buildingDetail : response " + new Gson().toJson(response));
        return response;
    }

}
