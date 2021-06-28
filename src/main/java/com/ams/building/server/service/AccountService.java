package com.ams.building.server.service;

import com.ams.building.server.response.AccountResponse;

import java.util.List;

public interface AccountService {

    void add(AccountResponse accountDTO);

    void update(AccountResponse accountDTO);

    void updateProfile(AccountResponse accountDTO);

    void delete(Long id);

    List<AccountResponse> find();

    AccountResponse getById(Long id);

    AccountResponse getByEmail(String email);

    Long count();

    Long counTotal();

    void changeAccountLock(long id);

}
