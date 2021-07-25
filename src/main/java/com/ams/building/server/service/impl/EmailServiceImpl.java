package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.SendEmailAccountDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.service.EmailService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

import static com.ams.building.server.utils.ValidateUtil.isEmail;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SendEmailAccountDAO sendEmailAccountDao;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration configuration;

    @Override
    @Async
    public void sendSimpleMessage(String email, String subject, String text) throws MessagingException {
        if (email.isEmpty()) return;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("tuan.nguyen@ekoios.vn");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);
        javaMailSender.send(message);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        if (StringUtils.isEmpty(token)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(email)) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (!isEmail(email)) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        Account account = sendEmailAccountDao.findAccountByEmail(email);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setResetPasswordToken(token);
        sendEmailAccountDao.save(account);
    }

    @Override
    public Account getByResetPasswordToken(String token) {
        return sendEmailAccountDao.findAccountByResetPasswordToken(token);
    }

    @Override
    public void updatePassWord(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account.setResetPasswordToken(null);
        sendEmailAccountDao.save(account);
    }

}
