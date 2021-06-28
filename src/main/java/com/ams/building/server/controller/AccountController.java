package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.dto.ResponseDTO;
import com.ams.building.server.response.AccountResponse;
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
@RequestMapping("api/admin/")
@CrossOrigin(origins = "*", maxAge = -1)
public class AccountController {
    private static final Logger logger = Logger.getLogger(AbsentController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(value = Constants.UrlPath.URL_API_LIST_ACCOUNT)
    public ResponseDTO<AccountResponse> find() {
        ResponseDTO<AccountResponse> responseDTO = new ResponseDTO<>();
        responseDTO.setData(accountService.find());
        responseDTO.setRecordsFiltered(accountService.count());
        responseDTO.setRecordsTotal(accountService.counTotal());
        logger.debug("findAccount: response " + new Gson().toJson(responseDTO));
        return responseDTO;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_LIST_ACCOUNT)
    public ResponseEntity<?> add(@ModelAttribute AccountResponse accountDTO) {
        logger.debug("add: request " + new Gson().toJson(accountDTO));
        accountDTO.setImage(FileStore.getFilePath(accountDTO.getMultipartFile(), "-user"));
        accountService.add(accountDTO);
        ResponseEntity<AccountResponse> response = new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
        logger.debug("add: response " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_UPDATE_ACCOUNT)
    public ResponseEntity<?> update(@ModelAttribute AccountResponse accountDTO) {
        logger.debug("update: request " + new Gson().toJson(accountDTO));
        accountDTO.setImage(FileStore.getFilePath(accountDTO.getMultipartFile(), "-user"));
        accountService.update(accountDTO);
        ResponseEntity<String> response = new ResponseEntity("Update success", HttpStatus.OK);
        logger.debug("update: response " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_GET_ACCOUNT)
    public ResponseEntity<?> get(
            @RequestParam(value = "id", required = false) Long id) {
        logger.debug("get: request " + id);
        AccountResponse accountResponse = accountService.getById(id);
        ResponseEntity<AccountResponse> response = new ResponseEntity<>(accountResponse, HttpStatus.OK);
        logger.debug("get: response " + new Gson().toJson(response));
        return response;
    }
}
