package com.ams.building.server.dao;

import com.ams.building.server.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<Account,Long> {

    Account getAccountByEmail(String email);

    Account getAccountById( Long id);

}
