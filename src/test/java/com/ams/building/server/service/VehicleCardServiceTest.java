package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.StatusVehicleCard;
import com.ams.building.server.bean.Vehicle;
import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.VehicleCardDAO;
import com.ams.building.server.dao.VehicleDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.VehicleCardClientRequest;
import com.ams.building.server.response.VehicleCardResponse;
import com.ams.building.server.response.VehicleTypeResponse;
import com.ams.building.server.service.impl.VehicleCardServiceImpl;
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

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToString;

@RunWith(PowerMockRunner.class)
public class VehicleCardServiceTest {

    @Mock
    VehicleCardDAO vehicleCardDAO;

    @Mock
    AccountDAO accountDAO;

    @Mock
    VehicleDAO vehicleDAO;

    @InjectMocks
    VehicleCardServiceImpl vehicleCardService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void searchVehicleCard() {
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);


        VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
                , "licensePlate", "red", "billingMonth"
                , new Date(), 1);
        Pageable pageable = PageRequest.of(1, 5);

        List<VehicleCard> vehicleCardList = Arrays.asList(vehicleCard);

        Page<VehicleCard> vehicleCards = new PageImpl<>(vehicleCardList, pageable, vehicleCardList.size());

        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
        Mockito.when(vehicleCardDAO.vehicleCardListWithoutStatus(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(vehicleCards);
        Mockito.when(vehicleCardDAO.vehicleCardListWithStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(vehicleCards);

        for (VehicleCard ad : vehicleCards) {
            VehicleCardResponse response = convertToCardResponse(ad);
            vehicleCardResponses.add(response);
        }
        vehicleCardService.searchVehicleCard(1, 5, "a", "b", "c", -1L);
        vehicleCardService.searchVehicleCard(1, 5, "a", "b", "c", 2L);
    }

    @Test
    public void detailVehicleCardTestSuccess() {
        // Init data
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);


        VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
                , "licensePlate", "red", "billingMonth"
                , new Date(), 1);

        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
        vehicleCardService.detailVehicleCard(1L);
    }

    @Test
    public void detailVehicleCardTestFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        vehicleCardService.detailVehicleCard(id);
    }

    @Test
    public void detailVehicleCardTestFailByIDNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ xe không tồn tại");
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(null);
        vehicleCardService.detailVehicleCard(Mockito.anyLong());
    }

    @Test
    public void updateStatusVehicleCardTestSuccess() {
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);


        VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
                , "licensePlate", "red", "billingMonth"
                , new Date(), 1);
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
        Mockito.doNothing().when(vehicleCardDAO).updateStatus(Mockito.any(), Mockito.any());
        vehicleCardService.updateStatusVehicleCard(1L, 1L);
    }

    @Test
    public void updateStatusVehicleCardTestFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        vehicleCardService.updateStatusVehicleCard(id, 1L);
    }

    @Test
    public void updateStatusVehicleCardTestFailByObjectNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ xe không tồn tại");
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(null);
        vehicleCardService.updateStatusVehicleCard(Mockito.anyLong(), 1L);
    }

    @Test
    public void removeVehicleCardTestSuccess() {
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);


        VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
                , "licensePlate", "red", "billingMonth"
                , new Date(), 1);
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
        Mockito.doNothing().when(vehicleCardDAO).cancelCard(Mockito.any(), Mockito.any());
        vehicleCardService.removeVehicleCard(1L);
    }

    @Test
    public void removeVehicleCardFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        vehicleCardService.removeVehicleCard(id);
    }

    @Test
    public void removeVehicleCardFailByObjectNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ xe không tồn tại");
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(null);
        vehicleCardService.removeVehicleCard(1L);
    }

    @Test
    public void listVehicleByTypeAndByAccountIdTestSuccess() {
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);


        VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
                , "licensePlate", "red", "billingMonth"
                , new Date(), 1);

        Pageable pageable = PageRequest.of(1, 5);

        List<VehicleCard> vehicleCardList = Arrays.asList(vehicleCard);

        Page<VehicleCard> vehicleCards = new PageImpl<>(vehicleCardList, pageable, vehicleCardList.size());
        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(account);
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(vehicleCard);
        Mockito.when(vehicleCardDAO.vehicleListByAccountIdAndTypeId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(vehicleCardList);
        Mockito.when(vehicleDAO.getById(Mockito.any())).thenReturn(vehicle);
        List<VehicleTypeResponse> responses = new ArrayList<>();
        vehicleCards.forEach(c -> responses.add(convertToVehicleTypeResponse(c)));
        vehicleCardService.listVehicleByTypeAndByAccountId(1L, 1L);
    }

    @Test
    public void listVehicleByTypeAndByAccountIdTestSFailDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Long idVehicleType = 1L;
        vehicleCardService.listVehicleByTypeAndByAccountId(id, idVehicleType);
    }

    @Test
    public void listVehicleByTypeAndByAccountIdTestSFailVehicleTypeEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long idVehicleType = null;
        vehicleCardService.listVehicleByTypeAndByAccountId(id, idVehicleType);
    }

    @Test
    public void listVehicleByTypeAndByAccountIdTestSFailAccountEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Long idVehicleType = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(null);
        vehicleCardService.listVehicleByTypeAndByAccountId(id, idVehicleType);
    }

    @Test
    public void listVehicleByTypeAndByAccountIdTestSFailVehicleEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Loại xe không tồn tại");
        Long id = 1L;
        Long idVehicleType = 1L;
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(account);
        Mockito.when(vehicleCardDAO.getVehicleCardById(Mockito.any())).thenReturn(null);
        vehicleCardService.listVehicleByTypeAndByAccountId(id, idVehicleType);
    }

    @Test
    public void searchVehicleCardByRoomNumber() {
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);

        Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);


        VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
                , "licensePlate", "red", "billingMonth"
                , new Date(), 1);
        Pageable pageable = PageRequest.of(1, 5);

        List<VehicleCard> vehicleCardList = Arrays.asList(vehicleCard);

        Page<VehicleCard> vehicleCards = new PageImpl<>(vehicleCardList, pageable, vehicleCardList.size());

        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
        Mockito.when(vehicleCardDAO.searchVehicleCardByRoomNumber(Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(vehicleCards);
        for (VehicleCard ad : vehicleCards) {
            VehicleCardResponse response = convertToCardResponse(ad);
            vehicleCardResponses.add(response);
        }
        vehicleCardService.searchVehicleCardByRoomNumber(1, 5, 1L, 1L);
    }

    @Test
    public void addVehicleCardTestSuccess() {
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        VehicleCardClientRequest clientRequest = VehicleCardClientRequest.builder().build();
        clientRequest.setVehicleId(1L);
        clientRequest.setVehicleName("Vehicle Name1");
        clientRequest.setVehicleBranch("Vehicle Branch1");
        clientRequest.setLicensePlate("License Plate 1");
        clientRequest.setVehicleColor("Color1");
        requests.add(clientRequest);
        Long accountId = 1L;
        VehicleCard vehicleCard = new VehicleCard();
        vehicleCard.setId(1L);
        List<VehicleCard> currentCard = new ArrayList<>();
        Mockito.when(vehicleCardDAO.findByLicense(Mockito.any()))
                .thenReturn(currentCard);
        Mockito.when(vehicleCardDAO.save(Mockito.any()))
                .thenReturn(vehicleCard);
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    @Test
    public void addVehicleCardTestFailByRequestsEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        Long accountId = 1L;
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    @Test
    public void addVehicleCardByBranchEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Nhãn xe không đưuọc để trống");
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        VehicleCardClientRequest clientRequest = VehicleCardClientRequest.builder().build();
        clientRequest.setVehicleId(1L);
        clientRequest.setVehicleName("Vehicle Name1");
        clientRequest.setVehicleBranch("");
        clientRequest.setLicensePlate("License Plate 1");
        clientRequest.setVehicleColor("Color1");
        requests.add(clientRequest);
        Long accountId = 1L;
        VehicleCard vehicleCard = new VehicleCard();
        vehicleCard.setId(1L);
        List<VehicleCard> currentCard = new ArrayList<>();
        Mockito.when(vehicleCardDAO.findByLicense(Mockito.any()))
                .thenReturn(currentCard);
        Mockito.when(vehicleCardDAO.save(Mockito.any()))
                .thenReturn(vehicleCard);
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    @Test
    public void addVehicleCardByLicensePlateEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Biển số xe không được để trống");
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        VehicleCardClientRequest clientRequest = VehicleCardClientRequest.builder().build();
        clientRequest.setVehicleId(1L);
        clientRequest.setVehicleName("Vehicle Name1");
        clientRequest.setVehicleBranch("Branch 1");
        clientRequest.setLicensePlate("");
        clientRequest.setVehicleColor("Color1");
        requests.add(clientRequest);
        Long accountId = 1L;
        VehicleCard vehicleCard = new VehicleCard();
        vehicleCard.setId(1L);
        List<VehicleCard> currentCard = new ArrayList<>();
        Mockito.when(vehicleCardDAO.findByLicense(Mockito.any()))
                .thenReturn(currentCard);
        Mockito.when(vehicleCardDAO.save(Mockito.any()))
                .thenReturn(vehicleCard);
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    @Test
    public void addVehicleCardByColorEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Màu xe không được để trống");
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        VehicleCardClientRequest clientRequest = VehicleCardClientRequest.builder().build();
        clientRequest.setVehicleId(1L);
        clientRequest.setVehicleName("Vehicle Name1");
        clientRequest.setVehicleBranch("Branch 1");
        clientRequest.setLicensePlate("Plate 1");
        clientRequest.setVehicleColor("");
        requests.add(clientRequest);
        Long accountId = 1L;
        VehicleCard vehicleCard = new VehicleCard();
        vehicleCard.setId(1L);
        List<VehicleCard> currentCard = new ArrayList<>();
        Mockito.when(vehicleCardDAO.findByLicense(Mockito.any()))
                .thenReturn(currentCard);
        Mockito.when(vehicleCardDAO.save(Mockito.any()))
                .thenReturn(vehicleCard);
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    @Test
    public void addVehicleCardByRegisterBeforeEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Biển số xe đã được đăng kí trước đó");
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        VehicleCardClientRequest clientRequest = VehicleCardClientRequest.builder().build();
        clientRequest.setVehicleId(1L);
        clientRequest.setVehicleName("Vehicle Name1");
        clientRequest.setVehicleBranch("Branch 1");
        clientRequest.setLicensePlate("Plate 1");
        clientRequest.setVehicleColor("Color 1");
        requests.add(clientRequest);
        Long accountId = 1L;
        VehicleCard vehicleCard = new VehicleCard();
        vehicleCard.setId(1L);
        List<VehicleCard> currentCard = new ArrayList<>();
        VehicleCard card = new VehicleCard();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setPrice(100000D);
        vehicle.setVehicleName("vehicle name 1");
        card.setVehicle(vehicle);
        card.setBillingMonth("08/2021");
        StatusVehicleCard statusVehicleCard = new StatusVehicleCard();
        statusVehicleCard.setId(1L);
        statusVehicleCard.setStatusName("status");
        card.setStatusVehicleCard(statusVehicleCard);
        currentCard.add(card);
        Mockito.when(vehicleCardDAO.findByLicense(Mockito.any()))
                .thenReturn(currentCard);
        Mockito.when(vehicleCardDAO.save(Mockito.any()))
                .thenReturn(vehicleCard);
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    @Test
    public void addVehicleCardByRegisterItemEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        List<VehicleCardClientRequest> requests = new ArrayList<>();
        VehicleCardClientRequest clientRequest = VehicleCardClientRequest.builder().build();
        clientRequest.setVehicleId(1L);
        clientRequest.setVehicleName("Vehicle Name1");
        clientRequest.setVehicleBranch("Branch 1");
        clientRequest.setLicensePlate("Plate 1");
        clientRequest.setVehicleColor("Color 1");
        requests.add(null);
        requests.add(clientRequest);
        Long accountId = 1L;
        VehicleCard vehicleCard = new VehicleCard();
        vehicleCard.setId(1L);
        List<VehicleCard> currentCard = new ArrayList<>();
        VehicleCard card = new VehicleCard();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setPrice(100000D);
        vehicle.setVehicleName("vehicle name 1");
        card.setVehicle(vehicle);
        card.setBillingMonth("08/2021");
        StatusVehicleCard statusVehicleCard = new StatusVehicleCard();
        statusVehicleCard.setId(1L);
        statusVehicleCard.setStatusName("status");
        card.setStatusVehicleCard(statusVehicleCard);
        currentCard.add(card);
        Mockito.when(vehicleCardDAO.findByLicense(Mockito.any()))
                .thenReturn(currentCard);
        Mockito.when(vehicleCardDAO.save(Mockito.any()))
                .thenReturn(vehicleCard);
        vehicleCardService.addVehicleCard(requests, accountId);
    }

    private VehicleCardResponse convertToCardResponse(VehicleCard card) {
        VehicleCardResponse response = VehicleCardResponse.builder().build();
        response.setId(card.getId());
        response.setVehicleOwner(card.getAccount().getName());
        response.setPhoneNumber(card.getAccount().getPhone());
        response.setLicensePlates(card.getLicensePlate());
        response.setType(card.getVehicle().getVehicleName());
        response.setColor(card.getVehicleColor());
        response.setStatus(card.getStatusVehicleCard().getStatusName());
        return response;
    }

    private VehicleTypeResponse convertToVehicleTypeResponse(VehicleCard card) {
        VehicleTypeResponse response = VehicleTypeResponse.builder().build();
        response.setVehicleBranch(card.getVehicleBranch());
        response.setColor(card.getVehicleColor());
        response.setLicensePlates(card.getLicensePlate());
        response.setStartDate(convertDateToString(card.getStartDate(), DD_MM_YYYY));
        response.setSeat(card.getVehicle().getId() == 3 ? "5" : card.getVehicle().getId() == 4 ? "7" : "");
        return response;
    }
}
