//package com.ams.building.server.service;
//
//import com.ams.building.server.bean.Account;
//import com.ams.building.server.bean.Position;
//import com.ams.building.server.bean.Role;
//import com.ams.building.server.bean.StatusVehicleCard;
//import com.ams.building.server.bean.Vehicle;
//import com.ams.building.server.bean.VehicleCard;
//import com.ams.building.server.dao.AccountDAO;
//import com.ams.building.server.dao.VehicleCardDAO;
//import com.ams.building.server.dao.VehicleDAO;
//import com.ams.building.server.response.ApiResponse;
//import com.ams.building.server.response.VehicleCardResponse;
//import com.ams.building.server.response.VehicleTypeResponse;
//import com.ams.building.server.service.impl.VehicleCardServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
//import static com.ams.building.server.utils.DateTimeUtils.convertDateToString;
//
//@RunWith(PowerMockRunner.class)
//public class VehicleCardServiceTest {
//
//    @Mock
//    VehicleCardDAO vehicleCardDAO;
//
//    @Mock
//    AccountDAO accountDAO;
//
//    @Mock
//    VehicleDAO vehicleDAO;
//
//    @InjectMocks
//    VehicleCardServiceImpl vehicleCardService;
//
//    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
//            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
//            , new Position(1L, "ch? h?", true), "1213", true);
//
//    Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);
//
//    Vehicle vehicle1 = null;
//
//    VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
//            , "licensePlate", "red", "billingMonth"
//            , new Date(), 1);
//
//    Pageable pageable = PageRequest.of(1, 5);
//
//    List<VehicleCard> vehicleCardList = Arrays.asList(vehicleCard);
//
//    Page<VehicleCard> vehicleCards = new PageImpl<>(vehicleCardList, pageable, vehicleCardList.size());
//
//    List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
//
//
//    @Test
//    public void searchVehicleCard() {
//        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
//        Calendar cal = Calendar.getInstance();
//        int month = cal.get(Calendar.MONTH) + 1;
//        int year = cal.get(Calendar.YEAR);
//        String monthStr = String.valueOf(month);
//        if (month < 10) {
//            monthStr = "0" + month;
//        }
//        String billingMonth = monthStr + "/" + year;
//
//        Mockito.when(vehicleCardDAO.vehicleCardListWithoutStatus(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(vehicleCards);
//        Mockito.when(vehicleCardDAO.vehicleCardListWithStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(vehicleCards);
//
//
//        for (VehicleCard ad : vehicleCards) {
//            VehicleCardResponse response = convertToCardResponse(ad);
//            vehicleCardResponses.add(response);
//        }
//        Long totalElement = vehicleCards.getTotalElements();
//        ApiResponse response = ApiResponse.builder().data(vehicleCardResponses).totalElement(totalElement).build();
//
//        vehicleCardService.searchVehicleCard(1, 5, "a", "b", "c", -1L);
//
//        vehicleCardService.searchVehicleCard(1, 5, "a", "b", "c", 2L);
//    }
//
//
//    @Test
//    public void detailVehicleCard() {
//        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
//        vehicleCardService.detailVehicleCard(1L);
//
//    }
//
//    @Test
//    public void updateStatusVehicleCard() {
//        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
//        Mockito.doNothing().when(vehicleCardDAO).updateStatus(Mockito.any(), Mockito.any());
//
//        vehicleCardService.updateStatusVehicleCard(1L, 1L);
//
//    }
//
//    @Test
//    public void removeVehicleCard() {
//
//        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
//        Mockito.doNothing().when(vehicleCardDAO).cancelCard(Mockito.any(), Mockito.any());
//
//        vehicleCardService.removeVehicleCard(1L);
//    }
//
//    @Test
//    public void listVehicleByTypeAndByAccountId() {
//
//        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(account);
//        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
//
//        Mockito.when(vehicleCardDAO.vehicleListByAccountIdAndTypeId(Mockito.any(), Mockito.any())).thenReturn(vehicleCardList);
//        Mockito.when(vehicleDAO.getById(Mockito.any())).thenReturn(vehicle);
//        List<VehicleTypeResponse> responses = new ArrayList<>();
//        vehicleCards.forEach(c -> responses.add(convertToVehicleTypeResponse(c)));
//
//        vehicleCardService.listVehicleByTypeAndByAccountId(1L, 1L);
//
//    }
//
//    @Test
//    public void searchVehicleCardByRoomNumber() {
//        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
//
//
//        Mockito.when(vehicleCardDAO.searchVehicleCardByRoomNumber(Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(vehicleCards);
//        for (VehicleCard ad : vehicleCards) {
//            VehicleCardResponse response = convertToCardResponse(ad);
//            vehicleCardResponses.add(response);
//        }
//        Long totalElement = vehicleCards.getTotalElements();
//        ApiResponse response = ApiResponse.builder().data(vehicleCardResponses).totalElement(totalElement).build();
//        vehicleCardService.searchVehicleCardByRoomNumber(1, 5, 1L, 1L);
//    }
//
//
//    private VehicleCardResponse convertToCardResponse(VehicleCard card) {
//        VehicleCardResponse response = VehicleCardResponse.builder().build();
//        response.setId(card.getId());
//        response.setVehicleOwner(card.getAccount().getName());
//        response.setPhoneNumber(card.getAccount().getPhone());
//        response.setLicensePlates(card.getLicensePlate());
//        response.setType(card.getVehicle().getVehicleName());
//        response.setColor(card.getVehicleColor());
//        response.setStatus(card.getStatusVehicleCard().getStatusName());
//        return response;
//    }
//
//    private VehicleTypeResponse convertToVehicleTypeResponse(VehicleCard card) {
//        VehicleTypeResponse response = VehicleTypeResponse.builder().build();
//        response.setVehicleBranch(card.getVehicleBranch());
//        response.setColor(card.getVehicleColor());
//        response.setLicensePlates(card.getLicensePlate());
//        response.setStartDate(convertDateToString(card.getStartDate(), DD_MM_YYYY));
//        response.setSeat(card.getVehicle().getId() == 3 ? "5" : card.getVehicle().getId() == 4 ? "7" : "");
//        return response;
//    }
//
//
//}
