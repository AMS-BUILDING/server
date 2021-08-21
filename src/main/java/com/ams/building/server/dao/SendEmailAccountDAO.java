package com.ams.building.server.dao;

import com.ams.building.server.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SendEmailAccountDAO extends JpaRepository<Account, Long> {


    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Account findAccountByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.resetPasswordToken = ?1")
    Account findAccountByResetPasswordToken(String token);

}
