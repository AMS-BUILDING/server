package com.ams.building.server.controller;

import com.ams.building.server.bean.Account;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.PropertyKeys;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.SendEmailAccountDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.PasswordRequest;
import com.ams.building.server.response.AccountAppResponse;
import com.ams.building.server.response.LoginResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.service.EmailService;
import com.ams.building.server.utils.FileStore;
import com.ams.building.server.utils.PropertiesReader;
import com.ams.building.server.utils.RandomNumber;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private SendEmailAccountDAO sendEmailAccountDao;

    @Autowired
    private EmailService emailService;

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_PROFILE_ACCOUNT)
    public ResponseEntity<?> updateAccountProfile(@RequestBody LoginResponse accountDTO) {
        logger.debug(" updateAccountProfile: request " + new Gson().toJson(accountDTO));
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        accountDTO.setId(currentUser.getId());
        accountDTO.setImage(FileStore.getFilePath(accountDTO.getMultipartFile(), "-user"));
        accountService.updateProfile(accountDTO);
        ResponseEntity<String> response = new ResponseEntity<>("Update profile success", HttpStatus.OK);
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_FORWARD_PASSWORD)
    public ResponseEntity<?> sendEmail(@RequestParam(name = "email", required = false, defaultValue = "") String email) throws MessagingException {
        String token = RandomNumber.getRandomNumberString();
        emailService.updateResetPasswordToken(token, email);
        String resetPassWordLink = "/forward-password?token=" + token;
        StringBuilder content = new StringBuilder();
        content.append("Hello");
        content.append(" <p>You have requested to reset your password </p>");
        content.append(" <p>Click the link below to change your password </p>");
        content.append(" <p><b><a href=\"" + resetPassWordLink + "\"> Change my Password </a><b></p>");
        content.append("<p> Ignore this email if you do remember your password , or you havav not made the request</p>");
        content.append("<p>   your code  is :   \"" + token + "\"   </p>");
        emailService.sendSimpleMessage(email, PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL), content.toString());
        ResponseEntity<String> response = new ResponseEntity<>("Send Link  Forward Password Success", HttpStatus.OK);
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestParam(name = "token", required = false, defaultValue = "") String token, @RequestParam(name = "password", required = false, defaultValue = "") String password) {
        Account account = sendEmailAccountDao.findAccountByResetPasswordToken(token);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        emailService.updatePassWord(account, password);
        ResponseEntity<String> response = new ResponseEntity<>("Reset Password Success", HttpStatus.OK);
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_CHANGE_PASSWORD)
    public void changePassword(@RequestBody LoginResponse loginResponse) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        loginResponse.setId(currentUser.getId());
        accountService.changePassword(loginResponse);
    }

    @GetMapping(Constants.UrlPath.URL_API_DETAIL_ACCOUNT)
    public ResponseEntity<?> detailAccountApp() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        AccountAppResponse accountAppResponse = accountService.detailAccountApp(id);
        ResponseEntity<AccountAppResponse> response = new ResponseEntity<>(accountAppResponse, HttpStatus.OK);
        logger.debug("detailAccountApp response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_ACCOUNT_APP_BY_NAME)
    public ResponseEntity<?> updateAccountByName(@RequestParam("name") String name) {
        logger.debug("detailAccountApp request: " + name);
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        accountService.updateAccountAppByName(name, id);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("detailAccountApp response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_ACCOUNT_APP_BY_DOB)
    public ResponseEntity<?> updateAccountByDob(@RequestParam("dob") String dob) {
        logger.debug("updateAccountByDob request: " + dob);
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        accountService.updateAccountAppByDob(dob, id);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("updateAccountByDob response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_ACCOUNT_APP_BY_IDENTIFY_CARD)
    public ResponseEntity<?> updateAccountByIdentifyCard(@RequestParam("identifyCard") String identifyCard) {
        logger.debug("updateAccountByIdentifyCard request: " + identifyCard);
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        accountService.updateAccountAppByIdentifyCard(identifyCard, id);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("updateAccountByIdentifyCard response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_ACCOUNT_APP_BY_PHONE_NUMBER)
    public ResponseEntity<?> updateAccountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        logger.debug("updateAccountByPhoneNumber request: " + phoneNumber);
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        accountService.updateAccountAppByPhoneNumber(phoneNumber, id);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("updateAccountByPhoneNumber response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_ACCOUNT_APP_BY_CURRENT_ADDRESS)
    public ResponseEntity<?> updateAccountByCurrentAddress(@RequestParam("currentAddress") String currentAddress) {
        logger.debug("updateAccountByCurrentAddress request: " + currentAddress);
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        accountService.updateAccountAppByCurrentAddress(currentAddress, id);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("updateAccountByCurrentAddress response: " + new Gson().toJson(response));
        return response;
    }

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
