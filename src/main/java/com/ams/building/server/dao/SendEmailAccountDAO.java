package com.ams.building.server.dao;

import com.ams.building.server.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SendEmailAccountDAO extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Account findAccountByEmail(String email);

    Account findAccountByResetPasswordToken(String token);
}