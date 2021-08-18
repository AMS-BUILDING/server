package com.ams.building.server.controller.admin;

import com.ams.building.server.bean.Account;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.PropertyKeys;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.SendEmailAccountDAO;
import com.ams.building.server.exception.RestApiException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<?> updateAccountProfile(@ModelAttribute LoginResponse accountDTO) {
        logger.debug(" updateAccountProfile: request " + new Gson().toJson(accountDTO));
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

}
