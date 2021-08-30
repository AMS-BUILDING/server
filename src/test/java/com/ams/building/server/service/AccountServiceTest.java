package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Notification;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.PasswordRequest;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.LoginResponse;
import com.ams.building.server.service.impl.AccountServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private NotificationDAO notificationDAO;

    @Mock
    private ApartmentDAO apartmentDao;

    @Mock
    private AccountDAO accountDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void addAccountSuccess() {
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("abc");
        response.setPassword("dfgdf");
        response.setName("ff");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        response.setImage("asdf");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        accountService.add(response);
    }

    @Test
    public void addAccountFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        accountService.add(null);
    }

    @Test
    public void addAccountNameEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setName("");
        response.setHomeTown("abc");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void addAccountHomeTownEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("");
        response.setName("ff");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void addAccountEmptyEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void addAccountPasswordEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Password không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void addAccountPhoneEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void addAccountIdentifyEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void addAccountCurrentEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("");
        accountService.add(response);
    }

    @Test
    public void addAccountEmailNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abcgsdfgom");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        accountService.add(response);
    }

    @Test
    public void addAccountPhoneNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("dfg");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        accountService.add(response);
    }

    @Test
    public void addAccountDuplicateAccount() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD đã được đăng ký");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0942578685");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        accountService.add(response);
    }

    @Test
    public void addAccountIdentifyNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD phải là số và phải có độ dài là 9 số hoặc 12 số");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("dsfg");
        response.setCurrentAddress("DFgfd");
        accountService.add(response);
    }

    @Test
    public void updateSuccess() {
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("abc");
        response.setPassword("dfgdf");
        response.setName("ff");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        response.setImage("asdf");
        response.setId(1L);
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(account);
        accountService.update(response);
    }

    @Test
    public void addAccountFail1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        accountService.update(null);
    }

    @Test
    public void addAccountNameEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setName("");
        response.setHomeTown("abc");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void addAccountHomeTownEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("");
        response.setName("ff");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void addAccountEmptyEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void addAccountPasswordEmpt1y() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Password không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void addAccountPhoneEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void addAccountIdentifyEmp1ty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void addAccountCurrentEm1pty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("");
        accountService.update(response);
    }

    @Test
    public void addAccountEmailNotR1ightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abcgsdfgom");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        accountService.update(response);
    }

    @Test
    public void addAccountPhoneNotRi1ghtFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("dfg");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        accountService.update(response);
    }

    @Test
    public void addAccounthgfDuplicateAccount() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0942578685");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        accountService.update(response);
    }

    @Test
    public void addAccountIdentifyNotRightFhormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("163444444");
        response.setCurrentAddress("DFgfd");
        accountService.update(response);
    }

    @Test
    public void changePasswordSuccess() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Mật khẩu không đúng");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("abc");
        response.setPassword("123456");
        response.setName("ff");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        response.setImage("asdf");
        response.setId(1L);
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        accountService.changePassword(response);
    }

    @Test
    public void addAccountFdfgail1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        accountService.changePassword(null);
    }

    @Test
    public void addAccountNameEmdgpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setName("");
        response.setHomeTown("abc");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountfghjfghjHomeTownEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("");
        response.setName("ff");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountEmptyEfhgjfgmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("dfgdf");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountfghjfghPasswordEmpt1y() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Password không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountPfghjfghhoneEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountIfghjfghdentifyEmp1ty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountCurrefghjfghjntEm1pty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountEmailNotRfghjghj1ightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abcgsdfgom");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        accountService.changePassword(response);
    }

    @Test
    public void addAccountPhoneNfghjfghjotRi1ghtFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("dfg");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        accountService.changePassword(response);
    }

    @Test
    public void fhgj() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0942578685");
        response.setIdentifyCard("444444444");
        response.setCurrentAddress("gsdfg");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        accountService.changePassword(response);
    }

    @Test
    public void addAccountIdentifyNotRightFhodfghfrmat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("dfhg");
        response.setName("ff");
        response.setPassword("fdg");
        response.setPhone("0977777777");
        response.setIdentifyCard("163444444");
        response.setCurrentAddress("DFgfd");
        accountService.changePassword(response);
    }

    @Test
    public void updateProfileSuccess() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        LoginResponse response = LoginResponse.builder().build();
        response.setEmail("abc@gmail.com");
        response.setHomeTown("abc");
        response.setPassword("123456");
        response.setName("ff");
        response.setPhone("0942578685");
        response.setIdentifyCard("164444444");
        response.setCurrentAddress("DFgfd");
        response.setImage("asdf");
        response.setId(1L);
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        accountService.updateProfile(response);
    }

    @Test
    public void deleteSuccess() {
        Long id = 1L;
        Long appartmentId = 1L;
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(1L, account, "", "", false, new Date()));
        Mockito.when(notificationDAO.listNotificationByAccountId(Mockito.anyLong())).thenReturn(notifications);
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(apartment);
        accountService.delete(id, appartmentId);
    }

    @Test
    public void deleteFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Long appartmentId = 1L;
        accountService.delete(id, appartmentId);
    }

    @Test
    public void deleteFailByIdobjectNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long appartmentId = null;
        accountService.delete(id, appartmentId);
    }

    @Test
    public void deleteFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Long appartmentId = 1L;
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(1L, account, "", "", false, new Date()));
        Mockito.when(notificationDAO.listNotificationByAccountId(Mockito.anyLong())).thenReturn(notifications);
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(apartment);
        accountService.delete(id, appartmentId);
    }

    @Test
    public void deleteFailByApartmentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        Long id = 1L;
        Long appartmentId = 1L;
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(1L, account, "", "", false, new Date()));
        Mockito.when(notificationDAO.listNotificationByAccountId(Mockito.anyLong())).thenReturn(notifications);
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(null);
        accountService.delete(id, appartmentId);
    }

    @Test
    public void deleteFailByAccountCanotRemove() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản này không thể xóa");
        Long id = 1L;
        Long appartmentId = 1L;
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(1L, account, "", "", false, new Date()));
        Mockito.when(notificationDAO.listNotificationByAccountId(Mockito.anyLong())).thenReturn(notifications);
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(apartment);
        accountService.delete(id, appartmentId);
    }

    @Test
    public void getByIdTestSuccess() {
        Long id = 1L;
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        accountService.getById(id);
    }

    @Test
    public void getByIdTestFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        accountService.getById(id);
    }

    @Test
    public void getByIdTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);

        accountService.getById(id);
    }

    @Test
    public void roleIdAccountByEmailTestSuccess() {
        String email = "dsfsd";
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        accountService.roleIdAccountByEmail(email);
    }

    @Test
    public void roleIdAccountByEmailTestFailByEmail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        String email = null;
        accountService.roleIdAccountByEmail(email);
    }

    @Test
    public void roleIdAccountByEmailTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        String email = "sdfg";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        accountService.roleIdAccountByEmail(email);
    }

    @Test
    public void changePasswordEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        accountService.changePassword(id, null);
    }

    @Test
    public void changePasswordPasswordEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Password không được để trống");
        Long id = 1L;
        PasswordRequest request = PasswordRequest.builder().build();
        request.setNewPassword("");
        request.setOldPassword("");
        accountService.changePassword(id, request);
    }

    @Test
    public void changePasswordPasswordNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Mật khẩu không đúng định dạng.");
        Long id = 1L;
        PasswordRequest request = PasswordRequest.builder().build();
        request.setNewPassword("2342");
        request.setOldPassword("sdgfdg");
        accountService.changePassword(id, request);
    }

    @Test
    public void changePasswordAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        PasswordRequest request = PasswordRequest.builder().build();
        request.setNewPassword("123456aA@");
        request.setOldPassword("123456aA@");
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        accountService.changePassword(id, request);
    }

    @Test
    public void validateApartmentOwnerTestSuccess() {
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("abcd");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccountEmail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email đã được đăng kí trước đó");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("abcd");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccountIdentify() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD đã được đăng ký");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("abcd");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(account);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccountIPhone() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại đã được đăng kí trước đó");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("abcd");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByDataEmptyl() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        accountService.validateApartmentOwner(null);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccountIPhonename() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccountIPhonenameDob() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccountIPhonenameDdfob() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByAccousdfntIPhonenameDdfob() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("ancd");
        request.setPhone("");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByCurrentAddress() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("");
        request.setPhone("fsfdg");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByIdentifyCard() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByHomeTown() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("dfghdf");
        request.setHomeTown("");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByEmailNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("dfghdf");
        request.setHomeTown("safd");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByphoneNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("abc@gmail.com");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("444444444");
        request.setHomeTown("safd");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByEIdentNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD phải là số và phải có độ dài là 9 số hoặc 12 số");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("abc@gmail.com");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("dfghdf");
        request.setHomeTown("safd");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.validateApartmentOwner(request);
    }

    @Test
    public void updateResidentTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        accountService.updateResident(null);
    }

    @Test
    public void updateResidentDOBEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        UpdateResidentRequest residentRequest = UpdateResidentRequest.builder().build();
        residentRequest.setIdentifyCard("2434");
        accountService.updateResident(residentRequest);
    }

    @Test
    public void updateResidentAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        UpdateResidentRequest residentRequest = UpdateResidentRequest.builder().build();
        residentRequest.setIdentifyCard("2434");
        residentRequest.setDob("2434");
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        accountService.updateResident(residentRequest);
    }

    @Test
    public void addListResident() {
        accountService.addListResident(new ArrayList<>());
    }

    @Test
    public void loadUserByUsername() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        String email = "";
        accountService.loadUserByUsername(email);
    }

    @Test
    public void loadUserByUfsername() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        String email = "sdfsdf";
        accountService.loadUserByUsername(email);
    }

    @Test
    public void loadUserByUfsgfername() {
        exceptionRule.expect(UsernameNotFoundException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        String email = "abc@gmail.com";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        accountService.loadUserByUsername(email);
    }

    @Test
    public void loadUserByUfgfdsgfername() {
        String email = "abc@gmail.com";
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        accountService.loadUserByUsername(email);
    }

    @Test
    public void addApartmentowner() {
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("abcd");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(accountDAO.save(Mockito.any())).thenReturn(account);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentdghdfgOwnerTestFailByAccountIdentify() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD đã được đăng ký");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("abcd");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(3L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(account);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmdfghdfghentOwnerTestFailByDataEmptyl() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        accountService.addApartmentOwner(null);
    }

    @Test
    public void validateApartmentdgfhdfghOwnerTestFailByAccountIPhonename() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("");
        request.setDob("16/04/1999");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmendfghdfgtOwnerTestFailByAccountIPhonenameDob() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("");
        request.setEmail("anc@gmail.com");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwdfghdfgnerTestFailByAccountIPhonenameDdfob() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("");
        request.setCurrentAddress("ancd");
        request.setPhone("0943333333");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmdfghdfgentOwnerTestFailByAccousdfntIPhonenameDdfob() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("ancd");
        request.setPhone("");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentdfghdfgOwnerTestFailByCurrentAddress() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Quê quán không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("");
        request.setPhone("fsfdg");
        request.setIdentifyCard("222222222");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwndfghdfgherTestFailByIdentifyCard() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("");
        request.setHomeTown("dfsg");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTesdfghfghtFailByHomeTown() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("dfghdf");
        request.setHomeTown("");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFaidgfhdgfhlByEmailNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("dfgh");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("dfghdf");
        request.setHomeTown("safd");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFfgdhdfghailByphoneNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số điện thoại là số có 10 chữ số và bắt đầu là số 0");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("abc@gmail.com");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("444444444");
        request.setHomeTown("safd");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

    @Test
    public void validateApartmentOwnerTestFailByEIdentNodfghtRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD phải là số và phải có độ dài là 9 số hoặc 12 số");
        ApartmentOwnerRequest request = ApartmentOwnerRequest.builder().build();
        request.setName("sdf");
        request.setDob("sdfg");
        request.setEmail("abc@gmail.com");
        request.setCurrentAddress("gsdfg");
        request.setPhone("fsfdg");
        request.setIdentifyCard("dfghdf");
        request.setHomeTown("safd");
        List<String> phones = new ArrayList<>();
        phones.add("dsfd");
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByIdentify(Mockito.anyString())).thenReturn(null);
        Mockito.when(accountDAO.getAccountByPhoneNumber(Mockito.anyString())).thenReturn(phones);
        accountService.addApartmentOwner(request);
    }

}
