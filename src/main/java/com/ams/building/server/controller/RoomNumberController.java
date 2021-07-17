package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.RoomNumberResponse;
import com.ams.building.server.service.RoomNumberService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class RoomNumberController {

    private static final Logger logger = Logger.getLogger(RoomNumberController.class);

    @Autowired
    private RoomNumberService roomNumberService;

    @GetMapping(value = Constants.UrlPath.URL_API_ROOM_NUMBER_SEARCH)
    public ResponseEntity<?> searchRoomNumber(@RequestParam(name = "floorId") Long floorId,
                                              @RequestParam(name = "blockId") Long blockId) {
        logger.debug("searchRoomNumber request : " + floorId + " - " + blockId);
        List<RoomNumberResponse> apiResponse = roomNumberService.roomNumberList(blockId, floorId);
        ResponseEntity<List<RoomNumberResponse>> responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("RoomNumberList response" + new Gson().toJson(responseEntity));
        return responseEntity;
    }

}
