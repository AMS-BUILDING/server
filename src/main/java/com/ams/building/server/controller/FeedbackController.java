package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.ListFeedbackResponse;
import com.ams.building.server.service.FeedbackService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/feedback/")
@CrossOrigin(origins = "*", maxAge = -1)
public class FeedbackController {
    private static final Logger logger = Logger.getLogger(AbsentController.class);

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_FEEDBACK)
    public ResponseEntity<?> searchFeedbackByNameAndCreateDate(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                               @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                               @RequestParam(value = "createDate", required = false) Date crateDate) {

        logger.debug("searchFeedback request : " + name + " - " + crateDate);

        Integer pageSize = 5;
        ListFeedbackResponse apiResponse = feedbackService.searchFeedbackByNameAndCreateDate(pageNo,pageSize,name,crateDate);
        ResponseEntity<ListFeedbackResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchFeedback  response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_List_FEEDBACK)
    public ResponseEntity<?> listAllFeedbacks() {

        Integer pageSize = 5;
        ListFeedbackResponse apiResponse = feedbackService.listAllFeedback(pageSize);
        ResponseEntity<ListFeedbackResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("findFeedback response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_INSERT_FEEDBACK)
    public ResponseEntity<?> insertFeedback(@RequestBody FeedbackRequest request) {

        logger.debug("insertFeedback request : " + new Gson().toJson(request));
        feedbackService.addFeedback(request);
        ResponseEntity response = new ResponseEntity(HttpStatus.CREATED);
        logger.debug("insertFeedback response : " + new Gson().toJson(response));
        return response;
    }
}
