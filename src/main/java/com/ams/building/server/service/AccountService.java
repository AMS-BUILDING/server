package com.ams.building.server.service;

import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.AccountAppResponse;
import com.ams.building.server.response.LoginResponse;

import java.util.List;

public interface AccountService {

    void add(LoginResponse loginResponse);

    Long addApartmentOwner(ApartmentOwnerRequest ownerRequest);

    List<Long> addListResident(List<ResidentRequest> residentRequestList);

    void updateResident(UpdateResidentRequest residentRequest);

    void update(LoginResponse loginResponse);

    void changePassword(LoginResponse loginResponse);

    void updateProfile(LoginResponse loginResponse);

    void delete(Long id);

    List<LoginResponse> find();

    LoginResponse getById(Long id);

    LoginResponse getByEmail(String email);

    Long count();

    void changeAccountLock(long id);

    void forwardPassword(String email);

    AccountAppResponse detailAccountApp(Long id);

}
