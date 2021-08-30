package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.dao.SendEmailAccountDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.service.impl.EmailServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(PowerMockRunner.class)
public class EmailServiceTest {

    @InjectMocks
    EmailServiceImpl emailService;

    @Autowired
    JavaMailSender javaMailSender;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    private SendEmailAccountDAO sendEmailAccountDao;

    Account account = new Account(1L, "loi", "loi@gmail.com", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);
    String newPassword = "@dhsjaiJ.4556";

    @Test
    public void updateResetPasswordToken() {
        Mockito.when(sendEmailAccountDao.findAccountByEmail(Mockito.any()))
                .thenReturn(account);

        Mockito.when(sendEmailAccountDao.save(account)).thenReturn(account);

        emailService.updateResetPasswordToken("1213", "loi@gmail.com");
    }

    @Test
    public void updateResetPasswordTokenTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        String email = "";
        String token = "";
        emailService.updateResetPasswordToken(email, token);
    }

    @Test
    public void updateResetPasswordTokenTestFailByEmailRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        String email = "aaa";
        String token = "aaa";
        emailService.updateResetPasswordToken(email, token);
    }

    @Test
    public void updateResetPasswordTokenTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        String email = "abc@gmail.com";
        String token = "aaa";
        Mockito.when(sendEmailAccountDao.findAccountByEmail(Mockito.anyString())).thenReturn(null);
        emailService.updateResetPasswordToken(email, token);
    }

    @Test
    public void getByResetPasswordToken() {
        Mockito.when(sendEmailAccountDao.findAccountByResetPasswordToken(Mockito.any()))
                .thenReturn(account);
        emailService.getByResetPasswordToken("1213");
    }

    @Test
    public void updatePassWord() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account.setResetPasswordToken(null);
        emailService.updatePassWord(account, newPassword);
    }

    @Test
    public void updatePassWordTestFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        emailService.updatePassWord(null, newPassword);
    }

    @Test
    public void updatePassWordTestFailByPasswordEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Password không được để trống");
        String newPassword = "";
        Mockito.when(sendEmailAccountDao.save(Mockito.any()))
                .thenReturn(account);
        emailService.updatePassWord(account, newPassword);
    }

    @Test
    public void updatePassWordTestFailByPasswordNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Mật khẩu không đúng định dạng.");
        String newPassword = "sdfg";
        Mockito.when(sendEmailAccountDao.save(Mockito.any()))
                .thenReturn(account);
        emailService.updatePassWord(account, newPassword);
    }

    @Test
    public void updatePassWordTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Password không được để trống");
        String newPassword = "";
        Mockito.when(sendEmailAccountDao.save(Mockito.any()))
                .thenReturn(account);
        emailService.updatePassWord(account, newPassword);
    }

}
