package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.response.FloorResponse;
import com.ams.building.server.service.ApartmentService;
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
public class BlockFloorController {

    private static final Logger logger = Logger.getLogger(BlockFloorController.class);

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping(value = Constants.UrlPath.URL_API_FLOOR_LIST)
    public ResponseEntity<?> floorList() {
        List<FloorResponse> responseList = apartmentService.floorList();
        ResponseEntity<List<FloorResponse>> response = new ResponseEntity<>(responseList, HttpStatus.OK);
        logger.debug("floorList response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_BLOCK_LIST)
    public ResponseEntity<?> blockList() {
        List<BlockResponse> responseList = apartmentService.blockList();
        ResponseEntity<List<BlockResponse>> response = new ResponseEntity<>(responseList, HttpStatus.OK);
        logger.debug("blockList response : " + new Gson().toJson(response));
        return response;
    }

}
