package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.PositionDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.EmployeeRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.EmployeeResponse;
import com.ams.building.server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private AccountDAO accountDao;

    @Autowired
    private PositionDAO positionDAO;

    @Override
    public ApiResponse searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Integer page, Integer size, String name, String phoneNumber, String identifyCard, Long position, String roles) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts;
        if (position == -1) {
            accounts = accountDao.searchAccountByNamePhoneIdentifyCardAndRole(name, phoneNumber, identifyCard, roles, pageable);
        } else {
            accounts = accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(name, phoneNumber, identifyCard, position, roles, pageable);
        }
        List<EmployeeResponse> accountDTOS = new ArrayList<>();
        accounts.forEach(a -> accountDTOS.add(convertEmployee(a)));
        Integer totalPage = accounts.getTotalPages();
        ApiResponse response = ApiResponse.builder().data(accountDTOS).totalPage(totalPage).build();
        return response;
    }

    @Override
    public void removeEmployee(Long id) {
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        accountDao.removeAccount(id);
    }

    @Override
    public void uppdateEmployee(Long accountId, Long positionId) {
        Account curentAccount = accountDao.getAccountById(accountId);
        if (Objects.isNull(curentAccount)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Position position = positionDAO.getOne(positionId);
        if (Objects.isNull(position)) {
            throw new RestApiException(StatusCode.POSITION_NOT_EXIST);
        }
        curentAccount.setPosition(position);
    }

    @Override
    public void addEmployee(EmployeeRequest request) {

    }

    @Override
    public EmployeeResponse getEmployeeById(Long accountId, String role) {
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(role)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountByIdAndRole(accountId, role);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        EmployeeResponse response = convertEmployee(account);
        return response;
    }

    private EmployeeResponse convertEmployee(Account account) {
        EmployeeResponse response = EmployeeResponse.builder().build();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setEmail(account.getEmail());
        response.setPhone(account.getPhone());
        response.setCurrentAddress(account.getCurrentAddress());
        response.setDob(account.getDob());
        response.setHomeTown(account.getHomeTown());
        response.setIdentityCard(account.getIdentifyCard());
        String gender = "";
        if (account.getGender() == true) {
            gender = Constants.AccountGender.GENDER_MALE;
        } else {
            gender = Constants.AccountGender.GENDER_FEMALE;
        }
        response.setGender(gender);
        if (Objects.isNull(account.getPosition())) {
            throw new RestApiException(StatusCode.POSITION_NOT_EXIST);
        }
        response.setPositionName(account.getPosition().getName());
        return response;
    }
}
