package com.ams.building.server.service;

import com.ams.building.server.response.AccountResponse;

import java.util.List;

public interface AccountService {

    void add(AccountResponse accountResponse);

    void update(AccountResponse accountResponse);

    void updateProfile(AccountResponse accountResponse);

    void delete(Long id);

    List<AccountResponse> find();

    AccountResponse getById(Long id);

    AccountResponse getByEmail(String  email);

    Long count();

    Integer countTotal();

    void changeAccountLock(long id);

}
