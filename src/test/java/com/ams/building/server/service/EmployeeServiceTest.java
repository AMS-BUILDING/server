package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.PositionDAO;
import com.ams.building.server.dao.RoleDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.EmployeeResponse;
import com.ams.building.server.service.impl.EmployeeServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RunWith(PowerMockRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Mock
    private AccountDAO accountDao;
    @Mock
    private PositionDAO positionDAO;
    @Mock
    private RoleDAO roleDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);

    Pageable pageable = PageRequest.of(1, 5);

    List<Account> accountList = Arrays.asList(account);

    Page<Account> Accounts = new PageImpl<>(accountList, pageable, accountList.size());

    Account currentAccount = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);


    @Test
    public void searchAccountByNamePhoneIdentifyCardAndRoleAndPosition() {
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRole(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        List<EmployeeResponse> accountDTOS = new ArrayList<>();
        Accounts.forEach(a -> accountDTOS.add(convertEmployee(a)));
        Long totalPage = Accounts.getTotalElements();

        ApiResponse response = ApiResponse.builder().data(accountDTOS).totalElement(totalPage).build();
        List<String>roles= new ArrayList<>();
        roles.add(RoleEnum.ROLE_LANDLORD.toString());
        employeeService.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(1, 5, "Manh", "0356974585", "026851698716", 3L,roles );
        employeeService.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(1, 5, "Manh", "0356974585", "026851698716", -1L, roles);
    }

    @Test
    public void removeEmployee() throws IOException {
//
//        // Expect Result
//        exceptionRule.expect(RestApiException.class);
//        exceptionRule.expectMessage("Dữ liệu trống");
//
//        Mockito.when(accountDao.getAccountById(Mockito.anyLong()))
//                .thenReturn(account);
//
//        // Expect Result
//        exceptionRule.expect(RestApiException.class);
//        exceptionRule.expectMessage("Tài khoản  không tồn tại");

        Mockito.when(accountDao.getAccountById(1L))
                .thenReturn(account);

        Mockito.doNothing().when(accountDao).removeAccount(1L);

        employeeService.removeEmployee(1L);

    }
//
//    @Test
//    public void updateEmployee() {
//
//        Mockito.when(accountDao.getAccountById(1L))
//                .thenReturn(currentAccount);
//
//        currentAccount.setDob("22201202");
//        currentAccount.setGender(true);
//        currentAccount.setHomeTown("homeTown");
//        currentAccount.setPhone("0983302977");
//        currentAccount.setCurrentAddress("currentAddress");
//        currentAccount.setName("Name");
//        currentAccount.setIdentifyCard("015623549568");
//        Position position = new Position(1L, "chủ hộ", true);
//        currentAccount.setPosition(position);
//        Role role = new Role(1L, "ROLE_ADMIN");
//        currentAccount.setRole(role);
//
//        Mockito.when(accountDao.save(currentAccount))
//                .thenReturn(account);
//        EmployeeRequest request = EmployeeRequest.builder()
//                .name("name")
//                .gender(true)
//                .dob("22201202")
//                .phoneNumber("0983302977")
//                .email("manh@gmail.com")
//                .identifyCard("015623549568")
//                .currentAddress("Address")
//                .homeTown("Town")
//                .position(2L)
//                .build();
//        employeeService.updateEmployee(1L, request);
//    }
//
//    @Test
//    public void addEmployee() {
//
//        Account account = new Account();
//        account.setIdentifyCard("015623549568");
//        account.setEmail("manh@gmail.com");
//        account.setDob("22201202");
//        account.setGender(true);
//        account.setHomeTown("HomeTown");
//        account.setPhone("0983302977");
//        account.setCurrentAddress("Address");
//        account.setName("Name");
//        account.setPassword(Constants.DEFAULT_PASSWORD);
//        account.setImage(FileStore.getDefaultAvatar());
//        account.setEnabled(true);
//        Position position = new Position(1L, "chủ hộ", true);
//        account.setPosition(position);
//        Role role = new Role(1L, "ROLE_ADMIN");
//        account.setRole(role);
//
//        Mockito.when(accountDao.save(account))
//                .thenReturn(account);
//        EmployeeRequest request = EmployeeRequest.builder()
//                .name("name")
//                .gender(true)
//                .dob("22201202")
//                .phoneNumber("0983302977")
//                .email("manh@gmail.com")
//                .identifyCard("015623549568")
//                .currentAddress("Address")
//                .homeTown("Town")
//                .position(2L)
//                .build();
//        employeeService.addEmployee(request);
//    }

    @Test
    public void getEmployeeById() {

        Mockito.when(accountDao.getAccountById(1L))
                .thenReturn(account);

        EmployeeResponse response = convertEmployee(account);

    }

    @Test
    public void downloadSearchEmployee() {
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRole(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        List<EmployeeResponse> accountDTOS = new ArrayList<>();
        Accounts.forEach(a -> accountDTOS.add(convertEmployee(a)));
        Long totalPage = Accounts.getTotalElements();

        ApiResponse response = ApiResponse.builder().data(accountDTOS).totalElement(totalPage).build();

//        employeeService.downloadSearchEmployee(Mockito.any(),"Manh","0356974585","026851698716",3L,"Landlord");
//        employeeService.downloadSearchEmployee(Mockito.any(),"Manh","0356974585","026851698716",-1L,"Landlord");
    }

    private String writeEmployee(Account account) {
        String content = "";
        try {
            String name = "";
            String gender = "";
            String phoneNumber = "";
            String email = "";
            String dob = "";
            String identifyCard = "";
            String currentAddress = "";
            String homeTown = "";
            String position = "";
            if (Objects.nonNull(account)) {
                name = account.getName();
                phoneNumber = account.getPhone();
                email = account.getEmail();
                dob = account.getDob();
                identifyCard = account.getIdentifyCard();
                currentAddress = account.getCurrentAddress();
                homeTown = account.getHomeTown();
                gender = account.getGender() == true ? "Male" : "Female";
                if (Objects.nonNull(account.getPosition())) {
                    position = account.getPosition().getName();
                }
            }
            content = name + "," + gender + "," + phoneNumber + "," + email + "," + dob + "," + identifyCard + "," + currentAddress + "," + homeTown + "," + position;

        } catch (Exception e) {
        }
        return content;
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
        response.setIdentifyCard(account.getIdentifyCard());
        response.setGender(account.getGender());
        if (Objects.isNull(account.getPosition())) {
            throw new RestApiException(StatusCode.POSITION_NOT_EXIST);
        }
        response.setPositionName(account.getPosition().getName());
        return response;
    }
}
