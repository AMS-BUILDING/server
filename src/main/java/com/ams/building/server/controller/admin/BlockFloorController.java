package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.response.FloorResponse;
import com.ams.building.server.response.RoomNumberResponse;
import com.ams.building.server.service.ApartmentService;
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
public class BlockFloorController {

    private static final Logger logger = Logger.getLogger(BlockFloorController.class);

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping(value = Constants.UrlPath.URL_API_FLOOR_LIST)
    public ResponseEntity<?> floorList(@RequestParam("blockId") Long blockId) {
        List<FloorResponse> responseList = apartmentService.floorList(blockId);
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

    @GetMapping(value = Constants.UrlPath.URL_API_ROOM_NUMBER_SEARCH)
    public ResponseEntity<?> searchRoomNumber(@RequestParam(name = "floorId") Long floorId,
                                              @RequestParam(name = "blockId") Long blockId) {
        logger.debug("searchRoomNumber request : " + floorId + " - " + blockId);
        List<RoomNumberResponse> apiResponse = apartmentService.roomNumberList(blockId, floorId);
        ResponseEntity<List<RoomNumberResponse>> responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("RoomNumberList response" + new Gson().toJson(responseEntity));
        return responseEntity;
    }

}
