package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.ResidentCardService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ResidentCardController {

    private static final Logger logger = Logger.getLogger(AbsentController.class);

    @Autowired
    private ResidentCardService residentCardService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_RESIDENT_CARD)
    public ResponseEntity<?> searchResidentCardByAccountId(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                           @RequestParam(value = "accountId") Long accountId) {
        logger.debug("searchResidentCardByAccountId request : " + accountId);
        Integer pageSize = 5;
        ApiResponse apiResponse = residentCardService.getResidentCardByAccountId(pageNo, pageSize, accountId);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchResidentCardByAccountId response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_UPDATE_RESIDENT_CARD + "/{id}")
    public ResponseEntity<?> updateResidentCard(@PathVariable("id") Long id, @RequestParam("statusId") Long statusId) {
        logger.debug("updateResidentCard request : " + id + "- " + statusId);
        residentCardService.updateStatusResidentCard(id, statusId);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("updateResidentCard response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_REMOVE_RESIDENT_CARD + "/{id}")
    public ResponseEntity<?> removeResidentCard(@PathVariable("id") Long id) {
        logger.debug("removeResidentCard request : " + id);
        residentCardService.removeResidentCard(id);
        ResponseEntity<String> response = new ResponseEntity<>("Remove success", HttpStatus.OK);
        logger.debug("removeResidentCard response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_RESIDENT_CARD)
    public ResponseEntity<?> addResidentCard(@RequestParam("email") String email) {
        logger.debug("addResidentCard request : " + email);
        residentCardService.addResidentCard(email);
        ResponseEntity<String> response = new ResponseEntity<>("Add success", HttpStatus.OK);
        logger.debug("addResidentCard response : " + new Gson().toJson(response));
        return response;
    }

}
