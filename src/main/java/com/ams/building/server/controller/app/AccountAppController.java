package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.request.PasswordRequest;
import com.ams.building.server.response.AccountAppResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AccountAppController {

    private static final Logger logger = Logger.getLogger(AccountAppController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(Constants.UrlPath.URL_API_CHANGE_PASSWORD_APP)
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest request) {
        logger.debug("changePassword request: " + new Gson().toJson(request));
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        accountService.changePassword(id, request);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("changePassword response: " + new Gson().toJson(response));
        return response;
    }

}
