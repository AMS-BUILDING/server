package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.PositionResponse;
import com.ams.building.server.service.PositionService;
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
@RequestMapping("api/position/")
@CrossOrigin(origins = "*", maxAge = -1)
public class PositionController {

    private static final Logger logger = Logger.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_POSITION)
    public ResponseEntity<?> positionList(@RequestParam(value = "show",required = false,defaultValue = "false") Boolean show) {
        logger.debug("positionList request : " + show);
        List<PositionResponse> apiResponse = positionService.positionByShow(show);
        ResponseEntity<List<PositionResponse>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("positionList response : " + new Gson().toJson(response));
        return response;
    }
}
