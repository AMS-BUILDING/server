package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.response.ManagerPersonResponse;
import com.ams.building.server.service.impl.ManagementPersonServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class ManagementPersonServiceITest {

    @InjectMocks
    ManagementPersonServiceImpl managementPersonService;
    @Mock
    AccountDAO accountDAO;

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);

    List<Account> managementAccount = Arrays.asList(account);

    @Test
    public void managementPersonServices() {
        Mockito.when(accountDAO.managementAccount())
                .thenReturn(managementAccount);
        List<ManagerPersonResponse> response = new ArrayList<>();
        managementAccount.forEach(a -> response.add(convertToPersonResponse(a)));
        managementPersonService.managementPersonServices();
    }

    private ManagerPersonResponse convertToPersonResponse(Account account) {
        ManagerPersonResponse response = ManagerPersonResponse.builder().build();
        response.setName(account.getName());
        response.setPhoneNumber(account.getPhone());
        response.setRoleName(account.getRole().getName().equals("ROLE_ADMIN") ? "Quản lí chung cư" : "Ban hỗ trợ dịch vụ");
        return response;
    }

}
