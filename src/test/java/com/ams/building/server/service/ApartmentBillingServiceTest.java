package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.DetailApartmentBilling;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusApartmentBilling;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DetailApartmentBillingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.service.impl.ApartmentBillingServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class ApartmentBillingServiceTest {

    @InjectMocks
    ApartmentBillingServiceImpl apartmentBillingService;

    @Mock
    DetailApartmentBillingDAO detailApartmentBillingDAO;

    @Mock
    AccountDAO accountDAO;

    @Mock
    ApartmentDAO apartmentDAO;

    @Mock
    ApartmentBillingDAO apartmentBillingDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getListFeeBillingByMonthAndAccountTestSuccess() {
        Long id = 1L;
        String billingMonth = "07/2021";
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "65"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        List<DetailApartmentBilling> billings = new ArrayList<>();
        DetailApartmentBilling detailApartmentBilling = new DetailApartmentBilling();
        ApartmentBilling apartmentBilling = new ApartmentBilling();
        apartmentBilling.setId(1L);
        apartmentBilling.setTotalPrice(1D);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdfs");
        apartmentBilling.setStatusApartmentBilling(statusApartmentBilling);
        detailApartmentBilling.setApartmentBilling(apartmentBilling);
        billings.add(detailApartmentBilling);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(apartmentBillingDAO.getDetailByMonth(Mockito.anyString(), Mockito.anyLong())).thenReturn(new ApartmentBilling());
        Mockito.when(detailApartmentBillingDAO.feeVehicleCardByAccountIdAndMonth(Mockito.anyLong(), Mockito.anyString())).thenReturn(0D);
        Mockito.when(detailApartmentBillingDAO.feeResidentCardByAccountIdAndMonth(Mockito.anyLong(), Mockito.anyString())).thenReturn(0D);
        Mockito.when(detailApartmentBillingDAO.feeServiceNameByAccountIdAndMonth(Mockito.anyLong(), Mockito.any())).thenReturn(new ArrayList<>());
        Mockito.when(detailApartmentBillingDAO.detailApartmentBillingByAccountIdAndMonth(Mockito.anyLong(), Mockito.anyString())).thenReturn(billings);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        apartmentBillingService.getListFeeBillingByMonthAndAccount(id, billingMonth);
    }

    @Test
    public void getListFeeBillingByMonthAndAccountTestFailDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        apartmentBillingService.getListFeeBillingByMonthAndAccount(id, null);
    }

    @Test
    public void getListFeeBillingByMonthAndAccountTestFailDataEmpty1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        apartmentBillingService.getListFeeBillingByMonthAndAccount(id, null);
    }

    @Test
    public void getListFeeBillingByMonthAndAccountTestFailAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        apartmentBillingService.getListFeeBillingByMonthAndAccount(id, "null");
    }

    @Test
    public void getListFeeBillingByMonthAndAccountTestFailAccountNotExist1() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Billing Month này không tồn tại");
        Long id = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(new Account());
        Mockito.when(apartmentBillingDAO.getDetailByMonth(Mockito.anyString(), Mockito.anyLong())).thenReturn(null);
        apartmentBillingService.getListFeeBillingByMonthAndAccount(id, "null");
    }

    @Test
    public void sendEmailToNotificationForResidentAboutFeeServiceInMonth(){
        apartmentBillingService.sendEmailToNotificationForResidentAboutFeeServiceInMonth();
    }

    @Test
    public void sendMailRemindForResident(){
        apartmentBillingService.sendMailRemindForResident();
    }
}
