package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.utils.FileStore;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account/")
@CrossOrigin(origins = "*", maxAge = -1)
public class AccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(value = Constants.UrlPath.URL_API_FIND_ACCOUNT)
    public ResponseEntity<?> find() {
        ApiResponse apiResponse = ApiResponse.builder().data(accountService.find()).totalElement(accountService.count()).build();
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("find Account: response " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_ADD_ACCOUNT)
    public ResponseEntity<?> add(@ModelAttribute AccountResponse accountDTO) {
        logger.debug("add Account: request " + new Gson().toJson(accountDTO));
        accountDTO.setImage(FileStore.getFilePath(accountDTO.getMultipartFile(), "-user"));
        accountService.add(accountDTO);
        ResponseEntity<AccountResponse> response = new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
        logger.debug("add Account: response " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_ACCOUNT)
    public ResponseEntity<?> update(@ModelAttribute AccountResponse accountResponse) {
        logger.debug("update Account: request " + new Gson().toJson(accountResponse));
        accountResponse.setImage(FileStore.getFilePath(accountResponse.getMultipartFile(), "-user"));
        accountService.update(accountResponse);
        ResponseEntity<String> response = new ResponseEntity<>("Update Success", HttpStatus.OK);
        logger.debug("update Account: response " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_GET_ACCOUNT)
    public ResponseEntity<?> get(
            @RequestParam(value = "id", required = false) Long id) {
        logger.debug("get Account: request " + id);
        AccountResponse accountResponse = accountService.getById(id);
        ResponseEntity<AccountResponse> response = new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
        logger.debug("get Account: request " + new Gson().toJson(response));
        return response;
    }
}
