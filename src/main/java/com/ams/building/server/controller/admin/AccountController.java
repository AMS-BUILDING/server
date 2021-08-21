package com.ams.building.server.controller.admin;

import com.ams.building.server.bean.Account;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.PropertyKeys;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.SendEmailAccountDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.ForwardPasswordRequest;
import com.ams.building.server.response.LoginResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.service.EmailService;
import com.ams.building.server.utils.FileStore;
import com.ams.building.server.utils.PropertiesReader;
import com.ams.building.server.utils.RandomNumber;
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
        content.append("Xin chào");
        content.append(" <p>Bạn có yêu cầu thay đổi mật khẩu </p>");
        Long roleId = accountService.roleIdAccountByEmail(email);
        if (roleId == 1 || roleId == 2) {
            content.append(" <p>Bạn vui lòng ấn vào link dưới đây</p>");
            content.append(" <p><b><a href=\"" + resetPassWordLink + "\"> Đổi mật khẩu của tôi </a><b></p>");
        }
        content.append("<p> Mã code của bạn :   \"" + token + "\"   </p>");
        content.append("<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn gửi nhầm yêu cầu</p>");
        emailService.sendSimpleMessage(email, PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL), content.toString());
        ResponseEntity<String> response = new ResponseEntity<>("Mã code đã được gửi đến mail. Vui lòng check", HttpStatus.OK);
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestBody ForwardPasswordRequest request) {
        Account account = sendEmailAccountDao.findAccountByResetPasswordToken(request.getToken());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        emailService.updatePassWord(account, request.getPassword());
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
