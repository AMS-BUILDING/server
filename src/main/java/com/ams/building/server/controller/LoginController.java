package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.LoginRequest;
import com.ams.building.server.response.LoginResponse;
import com.ams.building.server.response.TokenResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.sercurity.JwtTokenProvider;
import com.ams.building.server.service.AccountService;
import com.google.gson.Gson;
import org.apache.catalina.security.SecurityConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
@Import(SecurityConfig.class)
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AccountService accountService;

    @PostMapping(Constants.UrlPath.URL_API_LOGIN)
    public TokenResponse login(@RequestBody LoginRequest request) {
        logger.debug("addAbsent request : " + new Gson().toJson(request));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return jwtTokenProvider.createToken(request.getUsername());
        } catch (AuthenticationException e) {
            throw new RestApiException(StatusCode.LOGIN_FAIL);
        }
    }

    @GetMapping(Constants.UrlPath.URL_API_PROFILE)
    public ResponseEntity<?> getAccount() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        LoginResponse loginResponse = accountService.getById(currentUser.getId());
        ResponseEntity<LoginResponse> response = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        return response;
    }

}
