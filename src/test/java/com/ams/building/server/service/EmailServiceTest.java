package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.dao.SendEmailAccountDAO;
import com.ams.building.server.service.impl.EmailServiceImpl;
import freemarker.template.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(PowerMockRunner.class)

public class EmailServiceTest {

    @InjectMocks
    EmailServiceImpl emailService;
    @Mock
    JavaMailSender javaMailSender;
    @Mock
    Configuration configuration;
    @Mock
    private SendEmailAccountDAO sendEmailAccountDao;

    String token = "fdsfad41556";
    String email = "abcabc";
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
        Mockito.when(sendEmailAccountDao.save(account))
                .thenReturn(account);
        emailService.updatePassWord(account, newPassword);
    }

    @Test
    public void updatePassWordTestFail() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account.setResetPasswordToken(null);
        Mockito.when(sendEmailAccountDao.save(account))
                .thenReturn(account);
        emailService.updatePassWord(account, newPassword);
    }

}
