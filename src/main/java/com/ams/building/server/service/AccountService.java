package com.ams.building.server.service;

import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.PasswordRequest;
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

    void delete(Long id, Long apartmentId);

    LoginResponse getById(Long id);

    void changePassword(Long id, PasswordRequest request);

    void validateApartmentOwner(ApartmentOwnerRequest ownerRequest);

    void validateListResident(List<ResidentRequest> residentRequestList, ApartmentOwnerRequest ownerRequest);

    Long roleIdAccountByEmail(String email);

}
