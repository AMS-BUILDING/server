package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Notification;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusApartmentBilling;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.NotificationAppResponse;
import com.ams.building.server.response.NotificationResponse;
import com.ams.building.server.service.impl.NotificationSeviceImpl;
import com.ams.building.server.utils.HelperUtils;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class NotificationSeviceTest {

    @InjectMocks
    NotificationSeviceImpl notificationSevice;

    @Mock
    private NotificationDAO notificationDAO;

    @Mock
    private ApartmentBillingDAO apartmentBillingDAO;

    @Mock
    private ApartmentDAO apartmentDAO;

    @Mock
    private AccountDAO accountDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);

    Pageable pageable = PageRequest.of(1, 5);

    Notification notification = new Notification(1L, account, "title", "description", true, new Date());
    NotificationResponse response = NotificationResponse.builder().title("ffgffd").description("sdfgdfg").build();
    List<NotificationResponse> notificationList = Arrays.asList(response);

    Building building = new Building(1L, "buildingName", "address", "description");

    FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "blockName"), new Floor(1L, "Floor"));

    RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "250"), floorBlock, "roomName");

    Apartment apartment = new Apartment(1L, account, building, roomNumber);

    StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling(1L, "statusName");

    ApartmentBilling apartmentBilling = new ApartmentBilling(1L, apartment, statusApartmentBilling, 20D, "07/2021", false);

    List<ApartmentBilling> apartmentBillingList = Arrays.asList(apartmentBilling);

    @Test
    public void searchNotification() {
        Page<NotificationResponse> notifications = new PageImpl<>(notificationList, pageable, notificationList.size());
        Mockito.when(notificationDAO.searchNotificationByTitle(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(notifications);
        notificationSevice.searchNotification("title", 1, 4);
    }

    @Test
    public void addNotificationTestSuccess() {
        NotificationRequest request = NotificationRequest.builder().build();
        request.setTitle("abcde");
        request.setDescription("abcde");
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@gmail.com");
        account.setIdentifyCard("163446335");
        account.setPhone("0942578685");
        account.setCurrentAddress("HN");
        account.setHomeTown("HN");
        accounts.add(account);
        Mockito.when(accountDAO.getAccountByRoles()).thenReturn(accounts);
        notificationSevice.addNotification(request);
    }

    @Test
    public void addNotificationTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        NotificationRequest request = NotificationRequest.builder().build();
        request.setTitle("");
        request.setDescription("");
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@gmail.com");
        account.setIdentifyCard("163446335");
        account.setPhone("0942578685");
        account.setCurrentAddress("HN");
        account.setHomeTown("HN");
        accounts.add(account);
        Mockito.when(accountDAO.getAccountByRoles()).thenReturn(accounts);
        notificationSevice.addNotification(request);
    }

    @Test
    public void addNotificationTestFailByDescriptionEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phần miêu tả không được để trống");
        NotificationRequest request = NotificationRequest.builder().build();
        request.setTitle("aaaaa");
        request.setDescription("");
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@gmail.com");
        account.setIdentifyCard("163446335");
        account.setPhone("0942578685");
        account.setCurrentAddress("HN");
        account.setHomeTown("HN");
        accounts.add(account);
        Mockito.when(accountDAO.getAccountByRoles()).thenReturn(accounts);
        notificationSevice.addNotification(request);
    }

    @Test
    public void addNotificationTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        NotificationRequest request = NotificationRequest.builder().build();
        request.setTitle("aaaaa");
        request.setDescription("aaaa");
        List<Account> accounts = new ArrayList<>();
        Mockito.when(accountDAO.getAccountByRoles()).thenReturn(accounts);
        notificationSevice.addNotification(request);
    }

    @Test
    public void addNotificationTestFailByTitleEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phần tiêu đề không được để trống");
        NotificationRequest request = NotificationRequest.builder().build();
        request.setTitle("");
        request.setDescription("fghfgh");
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@gmail.com");
        account.setIdentifyCard("163446335");
        account.setPhone("0942578685");
        account.setCurrentAddress("HN");
        account.setHomeTown("HN");
        accounts.add(account);
        Mockito.when(accountDAO.getAccountByRoles()).thenReturn(accounts);
        notificationSevice.addNotification(request);
    }

    @Test
    public void listNotificationAppPrivateTestSuccess() {
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong()))
                .thenReturn(apartment);
        Mockito.when(apartmentBillingDAO.detailApartmentBuildingByMonth(Mockito.anyLong()))
                .thenReturn(apartmentBillingList);
        String apartmentSquarMetter = apartment.getRoomNumber().getTypeApartment().getTypeName();
        Long squarMetter = Long.valueOf(apartmentSquarMetter);
        Long fee = Long.valueOf(Constants.GeneralSerivce.FEE_GENERAL_SERVICE) * squarMetter;
        List<NotificationAppResponse> responses = new ArrayList<>();
        for (ApartmentBilling apartmentBilling : apartmentBillingList) {
            String month = apartmentBilling.getBillingMonth().split("/")[0];
            String year = apartmentBilling.getBillingMonth().split("/")[1];
            String monthNext = String.valueOf(Integer.valueOf(month) + 1);
            if (monthNext.length() == 1) {
                monthNext = "0" + monthNext;
            }
            if (month.equals("12")) {
                monthNext = "01";
                year = String.valueOf(Integer.valueOf(year) + 1);
            }
            Long feeTotal = apartmentBilling.getTotalPrice().longValue() + fee;
            NotificationAppResponse notificationAppResponse = NotificationAppResponse.builder().build();
            notificationAppResponse.setTitle("Thông báo phí căn hộ số " + apartment.getRoomNumber().getRoomName() + " tháng " + apartmentBilling.getBillingMonth() + " của căn hộ " + apartment.getRoomNumber().getRoomName());
            String mess = "Tổng số tiền quý cư dân cần hoàn thành thanh toán trong tháng là : " + HelperUtils.formatCurrentMoney(feeTotal);
            mess += ". Quý cư dân thanh toán trước ngày 10/" + monthNext + "/" + year;
            notificationAppResponse.setDescription(mess);
            notificationAppResponse.setTime("00:00");
            notificationAppResponse.setDate("01/" + monthNext + "/" + year);
            responses.add(notificationAppResponse);
            notificationSevice.listNotificationAppPrivate(1L);
        }
    }

    @Test
    public void listNotificationAppPrivateTestFailByAccountIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        notificationSevice.listNotificationAppPrivate(id);
    }

    @Test
    public void listNotificationAppPrivateTestFailByApartmentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        Long id = 1L;
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(null);
        notificationSevice.listNotificationAppPrivate(id);
    }

    @Test
    public void updateStatusTestSuccess() {
        Long id = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        List<Notification> listNotificationNotRead = new ArrayList<>();
        listNotificationNotRead.add(new Notification(1L, new Account(), "abc", "abc", true, new Date()));
        List<ApartmentBilling> apartmentBillingList = new ArrayList<>();
        apartmentBillingList.add(new ApartmentBilling(1L, new Apartment(), new StatusApartmentBilling(), 1D, "aaa", true));
        Mockito.when(notificationDAO.listNotificationNotRead(Mockito.anyLong())).thenReturn(listNotificationNotRead);
        Mockito.when(apartmentBillingDAO.listApartmentBillingNotRead(Mockito.anyLong())).thenReturn(apartmentBillingList);
        notificationSevice.updateStatus(id);
    }

    @Test
    public void updateStatusTestFailByIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        notificationSevice.updateStatus(id);
    }

    @Test
    public void updateStatusTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        notificationSevice.updateStatus(id);
    }

    @Test
    public void showNotificationNotReadTestSuccess() {
        Long accountId = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(notificationDAO.countNotificationNotRead(Mockito.anyLong())).thenReturn(1);
        Mockito.when(apartmentBillingDAO.countNotificationNotReadPrivate(Mockito.anyLong())).thenReturn(1);
        notificationSevice.showNotificationNotRead(accountId);
    }

    @Test
    public void showNotificationNotReadTestFailByIdNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        notificationSevice.showNotificationNotRead(id);
    }

    @Test
    public void showNotificationNotReadTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        notificationSevice.showNotificationNotRead(id);
    }

}

