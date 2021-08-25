package com.ams.building.server;

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
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.request.NotificationRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.NotificationAppResponse;
import com.ams.building.server.response.NotificationResponse;
import com.ams.building.server.service.impl.NotificationSeviceImpl;
import com.ams.building.server.utils.HelperUtils;
import org.junit.Test;
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

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.HH_MM;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;

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

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);

    Pageable pageable = PageRequest.of(1, 5);

    Notification notification = new Notification(1L, account, "title", "description", true, new Date());

    List<Notification> notificationList = Arrays.asList(notification);

    Page<Notification> notifications = new PageImpl<>(notificationList, pageable, notificationList.size());

    Building building = new Building(1L, "buildingName", "address", "description");

    FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "blockName"), new Floor(1L, "Floor"));

    RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "250"), floorBlock, "roomName");

    Apartment apartment = new Apartment(1L, account, building, roomNumber);

    StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling(1L, "statusName");

    ApartmentBilling apartmentBilling = new ApartmentBilling(1L, apartment, statusApartmentBilling, 20D, "07/2021",false);

    List<ApartmentBilling> apartmentBillingList = Arrays.asList(apartmentBilling);

    @Test
    public void searchNotification() {
        List<NotificationResponse> notificationDTOList = new ArrayList<>();
        Pageable pageable = PageRequest.of(1, 5);
        Mockito.when(notificationDAO.searchNotificationByTitle(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(notifications);
        notifications.forEach(s -> notificationDTOList.add(convert(s)));
        Long totalElements = notifications.getTotalElements();
        ApiResponse apiResponse = ApiResponse.builder().data(notificationDTOList).totalElement(totalElements).build();
        notificationSevice.searchNotification("title", 1, 4);
    }

    @Test
    public void addNotification() {
//
//        Notification notification = new Notification();
//        notification.setDescription("Description");
//        notification.setTitle("Title");
//        notification.setIsRead(false);
//        Mockito.when(notificationDAO.save(notification))
//                .thenReturn(notification);
//        NotificationRequest request = NotificationRequest.builder().description("description").title("title").build();
//
//        notificationSevice.addNotification(request);
    }

    @Test
    public void listNotificationAppGeneral() {
        List<NotificationAppResponse> notificationDTOList = new ArrayList<>();
        Mockito.when(notificationDAO.listNotification())
                .thenReturn(notificationList);
        notifications.forEach(s -> notificationDTOList.add(convertToNotificationApp(s)));
        notificationSevice.listNotificationAppGeneral();
    }

    @Test
    public void updateStatus() {
//        Mockito.when(notificationDAO.getById(Mockito.anyLong()))
//                .thenReturn(notification);
//        notification.setIsRead(true);
//        Mockito.when(notificationDAO.save(notification))
//                .thenReturn(notification);
//        notificationSevice.updateStatus(1L);
    }

    @Test
    public void listNotificationAppPrivate() {
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


    private NotificationResponse convert(Notification notification) {
        NotificationResponse response = NotificationResponse.builder().build();
        response.setDescription(notification.getDescription());
        response.setTitle(notification.getTitle());
        return response;
    }


    private NotificationAppResponse convertToNotificationApp(Notification notification) {
        NotificationAppResponse response = NotificationAppResponse.builder().build();
        response.setDescription(notification.getDescription());
        response.setTitle(notification.getTitle());
        response.setId(notification.getId());
        String date = convertDateToStringWithTimezone(notification.getCreatedDate(), DD_MM_YYYY, null);
        String time = convertDateToStringWithTimezone(notification.getCreatedDate(), HH_MM, null);
        response.setTime(time);
        response.setDate(date);
        response.setIsRead(notification.getIsRead());
        return response;
    }

}

