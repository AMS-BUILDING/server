package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.response.ManagerPersonResponse;
import com.ams.building.server.service.ManagementPersonService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagementPersonServiceImpl implements ManagementPersonService {

    private static final Logger logger = Logger.getLogger(ManagementPersonServiceImpl.class);

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public List<ManagerPersonResponse> managementPersonServices() {
        List<String> roles = new ArrayList<>();
        roles.add(RoleEnum.ROLE_ADMIN.toString());
        roles.add(RoleEnum.ROLE_MANAGER_SERVICE.toString());
        List<Account> managementAccount = accountDAO.managementAccount(roles);
        List<ManagerPersonResponse> response = new ArrayList<>();
        managementAccount.forEach(a -> response.add(convertToPersonResponse(a)));
        logger.debug("managementPersonServices :" + new Gson().toJson(response));
        return response;
    }

    private ManagerPersonResponse convertToPersonResponse(Account account) {
        ManagerPersonResponse response = ManagerPersonResponse.builder().build();
        response.setName(account.getName());
        response.setPhoneNumber(account.getPhone());
        response.setRoleName(account.getRole().getName().equals("ROLE_ADMIN") ? "Quản lí chung cư" : "Ban hỗ trợ dịch vụ");
        return response;
    }

}
