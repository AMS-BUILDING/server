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
import com.ams.building.server.request.EmployeeRequest;
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
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RunWith(PowerMockRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

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

    @Test
    public void searchAccountByNamePhoneIdentifyCardAndRoleAndPosition() {
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRole(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        List<EmployeeResponse> accountDTOS = new ArrayList<>();
        Accounts.forEach(a -> accountDTOS.add(convertEmployee(a)));

        List<String> roles = new ArrayList<>();
        roles.add(RoleEnum.ROLE_LANDLORD.toString());
        employeeService.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(1, 5, "Manh", "0356974585", "026851698716", 3L, roles);
        employeeService.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(1, 5, "Manh", "0356974585", "026851698716", -1L, roles);
    }

    @Test
    public void removeEmpployeeTestSuccess() {
        Long id = 1L;
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.removeEmployee(id);
    }

    @Test
    public void removeEmpployeeTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.removeEmployee(id);
    }

    @Test
    public void removeEmpployeeTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(null);
        employeeService.removeEmployee(id);
    }

    @Test
    public void getEmployeeByIdTestSuccess() {
        Long id = 1L;
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.getEmployeeById(id);
    }

    @Test
    public void getEmployeeByIdTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.getEmployeeById(id);
    }

    @Test
    public void getEmployeeByIdTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(null);
        employeeService.getEmployeeById(id);
    }

    @Test
    public void addEmployeeTestSuccess() {
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        List<String> phones = new ArrayList<>();
        Position position = new Position();
        position.setId(1L);
        Role role = new Role();
        role.setId(4L);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(positionDAO.getOne(Mockito.any())).thenReturn(position);
        Mockito.when(roleDAO.getOne(Mockito.any())).thenReturn(role);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        Mockito.when(accountDao.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeDataEmployeeEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        EmployeeRequest request = null;
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeDataOfEmployeeEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByNameEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByIdentifyCardEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByDobEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByCurrentAddressEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByHomeTownEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByPhoneEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByEmailEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByPositionEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Vị trí không tồn tại");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(null);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByPositionNotRightWithRoleEmployee() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Vị trí này không phù hợp với quyền là nhân viên");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(13L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByNEmailNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByPhoneNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("sdfsd");
        request.setEmail("abc@gmail.com");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByIdentifyCardNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD phải là số và phải có độ dài là 9 số hoặc 12 số");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("asdf");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(1L);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByDupilicateIdentifyCard() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD đã được đăng ký");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(1L);
        List<String> phones = new ArrayList<>();
        Position position = new Position();
        position.setId(1L);
        Role role = new Role();
        role.setId(4L);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(positionDAO.getOne(Mockito.any())).thenReturn(position);
        Mockito.when(roleDAO.getOne(Mockito.any())).thenReturn(role);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        Mockito.when(accountDao.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByPhoneNumberRegisterBefore() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại đã được đăng kí trước đó");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(1L);
        List<String> phones = new ArrayList<>();
        phones.add("0942578685");
        Position position = new Position();
        position.setId(1L);
        Role role = new Role();
        role.setId(4L);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(positionDAO.getOne(Mockito.any())).thenReturn(position);
        Mockito.when(roleDAO.getOne(Mockito.any())).thenReturn(role);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        Mockito.when(accountDao.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByEmailRegisterBefore() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email đã được đăng kí trước đó");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/1999");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(1L);
        List<String> phones = new ArrayList<>();
        Position position = new Position();
        position.setId(1L);
        Role role = new Role();
        role.setId(4L);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(positionDAO.getOne(Mockito.any())).thenReturn(position);
        Mockito.when(roleDAO.getOne(Mockito.any())).thenReturn(role);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        Mockito.when(accountDao.getAccountByEmail(Mockito.anyString())).thenReturn(new Account());
        employeeService.addEmployee(request);
    }

    @Test
    public void addEmployeeTestFailByEmployeeDontWorking() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Nhân viên không trong độ tuổi lao động");
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setName("abc");
        request.setIdentifyCard("163446335");
        request.setDob("16/04/2021");
        request.setCurrentAddress("abc");
        request.setHomeTown("abc");
        request.setPhoneNumber("0942578685");
        request.setEmail("abc@gmail.com");
        request.setPosition(1L);
        List<String> phones = new ArrayList<>();
        Position position = new Position();
        position.setId(1L);
        Role role = new Role();
        role.setId(4L);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(positionDAO.getOne(Mockito.any())).thenReturn(position);
        Mockito.when(roleDAO.getOne(Mockito.any())).thenReturn(role);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        Mockito.when(accountDao.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        employeeService.addEmployee(request);
    }

    @Test
    public void updateEmployeeTestSuccess() {
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmployeeTestFailByDataEmployee() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        EmployeeRequest request = EmployeeRequest.builder().build();
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmployeeTestFailByobjectNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(null);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByPositionNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Vị trí không tồn tại");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(null);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByPositionNotRightRole() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Vị trí này không phù hợp với quyền là nhân viên");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(5L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByNameEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByDobEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("aa");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByCurrentAddressEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("");
        request.setName("aa");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByHomeTownEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("sfds");
        request.setName("aa");
        request.setHomeTown("");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByPhoneNumberEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("sfds");
        request.setName("aa");
        request.setHomeTown("fg");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByPhoneNumberNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("sfds");
        request.setName("aa");
        request.setHomeTown("fg");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("afgd");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByIdentifyCardNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD phải là số và phải có độ dài là 9 số hoặc 12 số");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("sfds");
        request.setName("aa");
        request.setHomeTown("fg");
        request.setIdentifyCard("fgs");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmloyeeTestFailByidentifyCardEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("sfds");
        request.setName("aa");
        request.setHomeTown("fg");
        request.setIdentifyCard("");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmployeeTestFailByDuplicateIdentifyCard() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD đã được đăng ký");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Account account1 = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "765665666"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(account1);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmployeeTestEmployeeNotWorking() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Nhân viên không trong độ tuổi lao động");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/2021");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void updateEmployeeTestFailByPhoneResgiterBefore() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại đã được đăng kí trước đó");
        Long id = 1L;
        EmployeeRequest request = EmployeeRequest.builder().build();
        request.setEmail("abcd@gmail.com");
        request.setDob("16/04/1999");
        request.setGender(false);
        request.setCurrentAddress("Current Address");
        request.setName("name");
        request.setHomeTown("Home Town");
        request.setIdentifyCard("163446335");
        request.setPhoneNumber("0942578685");
        request.setPosition(1L);
        List<String> phones = new ArrayList<>();
        phones.add("0942578685");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDao.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDao.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        employeeService.updateEmployee(id, request);
    }

    @Test
    public void downloadSearchEmployeeTestSuccess() {
        HttpServletResponse response = new MockHttpServletResponse();
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRole(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        employeeService.downloadSearchEmployee(response, "", "", "", 1L, new ArrayList<>());
    }

    @Test
    public void downloadSearchEmployeeTestSuccessByPositionNull() {
        HttpServletResponse response = new MockHttpServletResponse();
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRole(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        Mockito.when(accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyObject()))
                .thenReturn(Accounts);
        employeeService.downloadSearchEmployee(response, "", "", "", -1L, new ArrayList<>());
    }

    @Test
    public void downloadSearchEmployeeTestFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Error Unknown");
        employeeService.downloadSearchEmployee(null, "", "", "", 1L, new ArrayList<>());
    }

    @Test
    public void downloadSearchEmployeeTestFailByData() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Error Unknown");
        HttpServletResponse response = new MockHttpServletResponse();
        employeeService.downloadSearchEmployee(response, "", "", "", 1L, new ArrayList<>());
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
