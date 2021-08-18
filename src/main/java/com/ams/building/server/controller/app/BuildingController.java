package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.BuildingResponse;
import com.ams.building.server.service.BuildingService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class BuildingController {

    private static final Logger logger = Logger.getLogger(BuildingController.class);

    @Autowired
    private BuildingService service;

    @GetMapping(Constants.UrlPath.URL_API_DETAIL_BUILDING_APP + "/{id}")
    public ResponseEntity<?> buildingDetail(@PathVariable("id") Long id) {
        logger.debug("buildingDetail : request " + id);
        BuildingResponse buildingResponse = service.detailBuilding(id);
        ResponseEntity<BuildingResponse> response = new ResponseEntity<>(buildingResponse, HttpStatus.OK);
        logger.debug("buildingDetail : response " + new Gson().toJson(response));
        return response;
    }

}
