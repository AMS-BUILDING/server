package com.ams.building.server.service;


import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.DetailSubService;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.ReasonDetailSubService;
import com.ams.building.server.bean.RequestService;
import com.ams.building.server.bean.ResidentCard;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.ServiceBean;
import com.ams.building.server.bean.StatusResidentCard;
import com.ams.building.server.bean.StatusServiceRequest;
import com.ams.building.server.bean.StatusVehicleCard;
import com.ams.building.server.bean.SubService;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.bean.Vehicle;
import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DetailSubServiceDAO;
import com.ams.building.server.dao.ReasonDetailSubServiceDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.dao.ResidentCardDAO;
import com.ams.building.server.dao.StatusRequestServiceDAO;
import com.ams.building.server.dao.VehicleCardDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.RequestServiceRequest;
import com.ams.building.server.response.DetailServiceRequestResponse;
import com.ams.building.server.response.ReasonDetailSubServiceResponse;
import com.ams.building.server.response.RequestServiceClientResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.response.StatusServiceResponse;
import com.ams.building.server.service.impl.RequestServiceServiceImpl;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.HH_MM;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;

@RunWith(PowerMockRunner.class)
public class RequestServiceServiceImplTest {

    @InjectMocks
    RequestServiceServiceImpl requestServiceServiceImpl;

    @Mock
    StatusRequestServiceDAO statusRequestServiceDAO;

    @Mock
    RequestServiceDAO requestServiceDAO;

    @Mock
    ApartmentDAO apartmentDAO;

    @Mock
    AccountDAO accountDAO;

    @Mock
    VehicleCardDAO vehicleCardDAO;

    @Mock
    ResidentCardDAO residentCardDAO;

    @Mock
    ReasonDetailSubServiceDAO reasonDetailSubServiceDAO;

    @Mock
    DetailSubServiceDAO detailSubServiceDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "ch? h?", true), "1213", true);

    Pageable pageable = PageRequest.of(1, 5);

    Building building = new Building(1L, "A01", "aa", "cccc");

    FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));

    RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");

    Apartment apartment = new Apartment(1L, account, building, roomNumber);


    ServiceBean serviceBean = new ServiceBean(1L, "service");

    SubService subService = new SubService(1L, serviceBean, "BUPFEE");

    DetailSubService detailSubService = new DetailSubService(1L, subService, "detail");

    ReasonDetailSubService reasonDetailSubService = new ReasonDetailSubService(1L, detailSubService,
            "reasonName", 1000D);

    RequestService requestService = new RequestService(1L, reasonDetailSubService
            , new StatusServiceRequest(1L, "a"), account, "a", new Date(), new Date(), new Date());

    List<StatusServiceRequest> statusServiceRequestList = Arrays.asList(new StatusServiceRequest(1L, "a"));
    List<RequestService> requestServicesList = Arrays.asList(requestService);

    Page<RequestService> requestServices = new PageImpl<RequestService>(requestServicesList, pageable, requestServicesList.size());

    @Test
    public void statusResponses() {
        List<StatusServiceResponse> responses = new ArrayList<>();
        Mockito.when(statusRequestServiceDAO.requestStatusList()).thenReturn(statusServiceRequestList);
        statusServiceRequestList.forEach(s -> responses.add(covertToStatusRequestResponse(s)));
        requestServiceServiceImpl.statusResponses();
    }

    @Test
    public void searchServiceRequest() {
        List<RequestServiceResponse> requestServiceResponses = new ArrayList<>();

        Mockito.when(requestServiceDAO.requestServicesNotStatus(Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(requestServices);

        Mockito.when(requestServiceDAO.requestServicesWithStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(requestServices);

        requestServices.forEach(s -> requestServiceResponses.add(covertToRequestResponse(s)));
        requestServiceServiceImpl.searchServiceRequest(1, 5, "a", "v", -1L);
        requestServiceServiceImpl.searchServiceRequest(1, 5, "a", "v", 2L);
    }

    @Test
    public void getRequestServiceByIdTestSuccess() {
        Long id = 1L;
        ReasonDetailSubService service = new ReasonDetailSubService();
        service.setId(1L);
        service.setDetailSubService(detailSubService);
        service.setReasonName("reason name");
        service.setPrice(10000D);
        Account account = new Account();
        account.setId(1L);
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setAccount(account);
        RoomNumber roomNumber = new RoomNumber();
        roomNumber.setId(1L);
        roomNumber.setRoomName("room number");
        FloorBlock floorBlock = new FloorBlock();
        floorBlock.setId(1L);
        Block block = new Block();
        block.setId(1L);
        block.setBlockName("abc");
        floorBlock.setBlock(block);
        roomNumber.setFloorBlock(floorBlock);
        apartment.setRoomNumber(roomNumber);
        RequestService requestService = new RequestService(1L, service
                , new StatusServiceRequest(1L, "a"), account, "a", new Date(), new Date(), new Date());
        Mockito.when(requestServiceDAO.findRequestServiceById(Mockito.any())).thenReturn(requestService);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong(), Mockito.anyString())).
                thenReturn(apartment);
        requestServiceServiceImpl.getRequestServiceById(id);
    }

    @Test
    public void getRequestServiceByIdTestFailByRequestService() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Yêu cầu dịch vụ này không tồn tại");
        Long id = 1L;
        Mockito.when(requestServiceDAO.findRequestServiceById(Mockito.any())).thenReturn(null);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong(), Mockito.anyString())).
                thenReturn(apartment);
        requestServiceServiceImpl.getRequestServiceById(id);
    }

    @Test
    public void getRequestServiceByIdTestFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        requestServiceServiceImpl.getRequestServiceById(id);
    }

    @Test
    public void updateStatusRequestSuccessTest() {
        Mockito.when(requestServiceDAO.findRequestServiceById(Mockito.any())).thenReturn(requestService);
        Mockito.doNothing().when(requestServiceDAO).updateStatus(Mockito.any(), Mockito.any());
        requestServiceServiceImpl.updateStatusRequest(1L, 1L);
    }

    @Test
    public void updateStatusRequestTestFailEmptyId() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Long requestId = 1L;
        requestServiceServiceImpl.updateStatusRequest(id, requestId);
    }

    @Test
    public void updateStatusRequestTestFailEmptyRequestId() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long requestId = null;
        requestServiceServiceImpl.updateStatusRequest(id, requestId);
    }

    StatusResidentCard statusResidentCard = new StatusResidentCard(1L, "a");

    ResidentCard residentCard = new ResidentCard(1L, account, statusResidentCard,
            "cardCode", 1000D, "price", new Date(), 1);

    List<ResidentCard> residentCardList = Arrays.asList(residentCard);

    @Test
    public void historyServiceResponse() {
        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(account);
        List<Long> status = new ArrayList<>();
        status.add(1L);
        List<Integer> isUses = new ArrayList<>();
        isUses.add(1);
        isUses.add(0);
        Mockito.when(residentCardDAO.residentCardRegister(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(residentCardList);
        Mockito.when(requestServiceDAO.requestServiceByAccountId(Mockito.any(), Mockito.any())).thenReturn(requestServicesList);
        List<RequestServiceClientResponse> responses = new ArrayList<>();
        requestServicesList.forEach(s -> responses.add(convertToHistoryResponse(s)));
        for (ResidentCard residentCard : residentCardList) {
            RequestServiceClientResponse requestServiceClientResponse = convertResidentCardToResponse(residentCard);
            responses.add(requestServiceClientResponse);
        }
        responses.sort(Comparator.comparing(RequestServiceClientResponse::getMinutes));
        requestServiceServiceImpl.historyServiceResponse(1L, 1L);
    }

    @Test
    public void historyServiceResponseTestIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long requestId = null;
        requestServiceServiceImpl.historyServiceResponse(id, requestId);
    }

    @Test
    public void historyServiceResponseTestRequestIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Long requestId = 1L;
        requestServiceServiceImpl.historyServiceResponse(id, requestId);
    }

    @Test
    public void historyServiceResponseTestFailAccountExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Long requestId = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.any())).thenReturn(null);
        requestServiceServiceImpl.historyServiceResponse(id, requestId);
    }

    Vehicle vehicle = new Vehicle(1L, "xe may", 10000D, 1000D);
    VehicleCard vehicleCard = new VehicleCard(1L, vehicle, account, new StatusVehicleCard(1L, "sucess"), "abc"
            , "licensePlate", "red", "billingMonth"
            , new Date(), 1);

    @Test
    public void detailServiceRequestTestSuccessWithTypeRequestOne() {
        Long typeRequest = 0L;
        DetailServiceRequestResponse response = DetailServiceRequestResponse.builder().build();
        Mockito.when(requestServiceDAO.findRequestServiceById(Mockito.any())).thenReturn(requestService);
        if (typeRequest == 1) {
            String message = requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
            if (requestService.getReasonDetailSubService().getDetailSubService().getService().getId() == 9) {
                message += requestService.getReasonDetailSubService().getDetailSubService().getDetailSubServiceName().toLowerCase() + " v?  " + requestService.getReasonDetailSubService().getReasonName().toLowerCase();
            }
            response.setStatusId(requestService.getStatusServiceRequest().getId());
            response.setServiceName(message);
        } else if (typeRequest == 2) {
            Mockito.when(vehicleCardDAO.getById(Mockito.any())).thenReturn(vehicleCard);
            String message = "??ng k? th? xe";
            response.setStatusId(vehicleCard.getStatusVehicleCard().getId());
            response.setServiceName(message);
        } else {
            Mockito.when(residentCardDAO.getById(Mockito.any())).thenReturn(residentCard);
            String message = "??ng k? th? c?n h? " + residentCard.getCardCode();
            response.setServiceName(message);
            response.setStatusId(residentCard.getStatusResidentCard().getId());
        }
        requestServiceServiceImpl.detailServiceRequest(1L, 1L);
    }

    @Test
    public void detailServiceRequestTestSuccessWithTypeRequestSecond() {
        Long serviceRequestId = 1L;
        Long typeRequest = 2L;
        Mockito.when(vehicleCardDAO.getById(Mockito.any())).thenReturn(vehicleCard);
        requestServiceServiceImpl.detailServiceRequest(serviceRequestId, typeRequest);
    }

    @Test
    public void detailServiceRequestTestSuccessWithTypeRequestThree() {
        Long serviceRequestId = 1L;
        Long typeRequest = 3L;
        Mockito.when(residentCardDAO.getById(Mockito.any())).thenReturn(residentCard);
        requestServiceServiceImpl.detailServiceRequest(serviceRequestId, typeRequest);
    }

    @Test
    public void detailServiceRequestTestFailById() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long typeRequest = 1L;
        Long id = null;
        requestServiceServiceImpl.detailServiceRequest(id, typeRequest);
    }

    @Test
    public void detailServiceRequestTestFailByRequestServiceNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Yêu cầu dịch vụ này không tồn tại");
        Long typeRequest = 1L;
        Long id = 1L;
        Mockito.when(requestServiceDAO.findRequestServiceById(Mockito.anyLong())).thenReturn(null);
        requestServiceServiceImpl.detailServiceRequest(id, typeRequest);
    }

    @Test
    public void detailServiceRequestTestFailByRequestServiceNotExistWithTypeRequestTwo() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Loại xe không tồn tại");
        Long typeRequest = 2L;
        Long id = 1L;
        Mockito.when(vehicleCardDAO.getById(Mockito.anyLong())).thenReturn(null);
        requestServiceServiceImpl.detailServiceRequest(id, typeRequest);
    }

    @Test
    public void detailServiceRequestTestFailByRequestServiceNotExistWithTypeRequestOther() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ căn hộ không tồn tại");
        Long typeRequest = 3L;
        Long id = 1L;
        Mockito.when(residentCardDAO.getById(Mockito.anyLong())).thenReturn(null);
        requestServiceServiceImpl.detailServiceRequest(id, typeRequest);
    }

    @Test
    public void findRequestServiceByAccountIdTestSuccess() {
        Mockito.when(requestServiceDAO.findRequestServiceByAccountId(Mockito.any(), Mockito.anyString())).thenReturn(requestServicesList);
        List<VehicleCard> vehicleCardList = new ArrayList<>();
        StatusVehicleCard statusVehicleCard = new StatusVehicleCard();
        statusVehicleCard.setId(1L);
        statusVehicleCard.setStatusName("abc");
        VehicleCard card = new VehicleCard();
        card.setId(1L);
        card.setStatusVehicleCard(statusVehicleCard);
        card.setVehicle(vehicle);
        card.setVehicleColor("color");
        card.setVehicleBranch("branch");
        card.setAccount(account);
        card.setBillingMonth("07/2021");
        card.setIsUse(1);
        card.setStartDate(new Date());
        card.setLicensePlate("plate");
        vehicleCardList.add(card);
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "ch? h?", true), "1213", true);
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setAccount(account);
        RoomNumber roomNumber = new RoomNumber();
        roomNumber.setId(1L);
        roomNumber.setRoomName("room number");
        FloorBlock floorBlock = new FloorBlock();
        floorBlock.setId(1L);
        Block block = new Block();
        block.setId(1L);
        block.setBlockName("abc");
        floorBlock.setBlock(block);
        roomNumber.setFloorBlock(floorBlock);
        apartment.setRoomNumber(roomNumber);
        List<Long> status = new ArrayList<>();
        status.add(1L);
        status.add(2L);
        List<Integer> isUses = new ArrayList<>();
        isUses.add(1);

        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).
                thenReturn(apartment);
        Mockito.when(vehicleCardDAO.vehicleCardRegister(Mockito.any(), Mockito.anyString())).thenReturn(vehicleCardList);
        Mockito.when(residentCardDAO.residentCardRegister(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(residentCardList);

        requestServiceServiceImpl.findRequestServiceByAccountId(1L);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestSuccessWithRequestOne() {
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = 1L;

        Mockito.when(requestServiceDAO.findRequestServiceById(Mockito.anyLong())).
                thenReturn(requestService);
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestSuccessWithRequestSecond() {
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = 2L;

        Mockito.when(vehicleCardDAO.getById(Mockito.anyLong())).
                thenReturn(vehicleCard);
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestSuccessWithRequestThree() {
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = 3L;

        Mockito.when(residentCardDAO.getById(Mockito.anyLong())).
                thenReturn(residentCard);
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestFailStatusIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long statusId = null;
        Long requestId = 1L;
        Long typeRequest = 3L;
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestFailRequestIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long statusId = 1L;
        Long requestId = null;
        Long typeRequest = 3L;
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestFailTypeRequestIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = null;
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestFailRequestServiceEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Yêu cầu dịch vụ này không tồn tại");
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = 1L;
        Mockito.when(residentCardDAO.getById(Mockito.anyLong())).
                thenReturn(null);
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestFailVehicleCardEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ xe không tồn tại");
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = 2L;
        Mockito.when(vehicleCardDAO.getById(Mockito.anyLong())).
                thenReturn(null);
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void updateStatusRequestByTypeRequestTestFailByResidentCardEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ căn hộ không tồn tại");
        Long statusId = 1L;
        Long requestId = 1L;
        Long typeRequest = 3L;
        Mockito.when(requestServiceDAO.getById(Mockito.anyLong())).
                thenReturn(null);
        requestServiceServiceImpl.updateStatusRequestByTypeRequest(statusId, requestId, typeRequest);
    }

    @Test
    public void listHourTestSuccess() {
        requestServiceServiceImpl.listHours();
    }

    @Test
    public void addRequestServiceSuccessStatusTestSuccessWithRequestSmallerThanFour() {
        RequestServiceRequest request = RequestServiceRequest.builder().build();
        request.setEndDate("2021/08/08");
        request.setStartDate("2021/08/08");
        request.setAccountId(1L);
        request.setReasonDetailSubServiceId(1L);
        Mockito.when(requestServiceDAO.findRequestServiceByStartDateAndReasonDetailSubServiceId(Mockito.any(), Mockito.anyLong())).thenReturn(null);
        requestServiceServiceImpl.addRequestServiceSuccessStatus(request);
    }

    @Test
    public void addRequestServiceSuccessStatusTestSuccessWithRequestEqualFive() {
        RequestServiceRequest request = RequestServiceRequest.builder().build();
        request.setEndDate("2021/08/08");
        request.setStartDate("2021/08/08");
        request.setAccountId(5L);
        request.setReasonDetailSubServiceId(5L);
        requestServiceServiceImpl.addRequestServiceSuccessStatus(request);
    }

    @Test
    public void addRequestServiceSuccessStatusTestSuccessWithRequestBiggerFive() {
        RequestServiceRequest request = RequestServiceRequest.builder().build();
        request.setEndDate("2021/08/08");
        request.setStartDate("2021/08/08");
        request.setAccountId(5L);
        request.setReasonDetailSubServiceId(6L);
        requestServiceServiceImpl.addRequestServiceSuccessStatus(request);
    }

    @Test
    public void addRequestServiceSuccessStatusTestFailServiceEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Lịch đăng kí bị trùng");
        RequestServiceRequest request = RequestServiceRequest.builder().build();
        request.setEndDate("2021/08/08");
        request.setStartDate("2021/08/08");
        request.setAccountId(1L);
        request.setReasonDetailSubServiceId(1L);
        requestServiceServiceImpl.addRequestServiceSuccessStatus(request);
        RequestService serviceRequest = new RequestService();
        serviceRequest.setId(1L);
        Mockito.when(requestServiceDAO.findRequestServiceByStartDateAndReasonDetailSubServiceId(Mockito.any(), Mockito.anyLong())).thenReturn(serviceRequest);
        requestServiceServiceImpl.addRequestServiceSuccessStatus(request);
    }

    @Test
    public void addRequestServiceSuccessStatusTestFailServiceNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        requestServiceServiceImpl.addRequestServiceSuccessStatus(null);
    }

    @Test
    public void getPriceByReasonDetailSubServiceIdTestSuccess() {
        Long id = 1L;
        Mockito.when(reasonDetailSubServiceDAO.getReasonDetailSubServiceById(Mockito.anyLong())).thenReturn(reasonDetailSubService);
        requestServiceServiceImpl.getPriceByReasonDetailSubServiceId(id);
    }

    @Test
    public void getPriceByReasonDetailSubServiceIdTestFailByIdNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Mockito.when(reasonDetailSubServiceDAO.getReasonDetailSubServiceById(Mockito.anyLong())).thenReturn(reasonDetailSubService);
        requestServiceServiceImpl.getPriceByReasonDetailSubServiceId(id);
    }

    @Test
    public void getPriceByReasonDetailSubServiceIdTestFailByReasonNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Lí do cụ thể của dịch vụ không tồn tại");
        Long id = 1L;
        Mockito.when(reasonDetailSubServiceDAO.getReasonDetailSubServiceById(Mockito.anyLong())).thenReturn(null);
        requestServiceServiceImpl.getPriceByReasonDetailSubServiceId(id);
    }

    @Test
    public void getListReasonByDetailSubServiceIdTestFailByIdNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        requestServiceServiceImpl.getDetailSubServiceBySubServiceId(id);
    }

    @Test
    public void getListReasonByDetailSubServiceIdTestSuccess() {
        Long id = 1L;
        List<DetailSubService> detailSubServices = new ArrayList<>();
        DetailSubService subService = new DetailSubService();
        subService.setId(1L);
        subService.setDetailSubServiceName("");
        SubService service = new SubService();
        service.setId(1L);
        subService.setService(service);
        detailSubServices.add(subService);
        Mockito.when(detailSubServiceDAO.getDetailSubServiceBySubServiceId(Mockito.anyLong())).thenReturn(detailSubServices);
        requestServiceServiceImpl.getDetailSubServiceBySubServiceId(id);
    }

    @Test
    public void getListReasonByDetailSubServiceIdTestFailByIdNotExistSecond() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        requestServiceServiceImpl.getListReasonByDetailSubServiceId(id);
    }

    @Test
    public void getListReasonByDetailSubServiceIdTestSuccessSecond() {
        Long id = 1L;
        List<ReasonDetailSubService> detailSubServices = new ArrayList<>();
        ReasonDetailSubService subService = new ReasonDetailSubService();
        subService.setId(1L);
        subService.setReasonName("abc");
        subService.setPrice(100D);
        DetailSubService detailSubService = new DetailSubService();
        detailSubService.setId(1L);
        detailSubService.setDetailSubServiceName("abcd");
        subService.setDetailSubService(detailSubService);
        Mockito.when(reasonDetailSubServiceDAO.getListReasonByDetailSubServiceId(Mockito.anyLong())).thenReturn(detailSubServices);
        requestServiceServiceImpl.getListReasonByDetailSubServiceId(id);
    }

    private RequestServiceClientResponse convertResidentCardToResponse(ResidentCard residentCard) {
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        Date currentTime = new Date();

        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any())).thenReturn(apartment);

        RoomNumber roomNumber = apartment.getRoomNumber();
        Long diff = currentTime.getTime() - residentCard.getStartDate().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " ph?t tr??c";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " gi? tr??c";
        } else {
            time = convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null);
        }
        response.setId(residentCard.getId());
        response.setServiceName("Y?u c?u ???c c?p th? c?n h?  - " + convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null));
        response.setDescription("Y?u c?u ???c c?p  th? c?n h? c?a c?n h? " + roomNumber.getRoomName() + " l?c " + convertDateToStringWithTimezone(residentCard.getStartDate(), HH_MM, null));
        response.setTime(time);
        response.setStatusId(residentCard.getStatusResidentCard().getId());
        response.setTypeRequest(3L);
        response.setMinutes(minutes);
        return response;
    }

    private ReasonDetailSubServiceResponse convertReasonDetailSubService(ReasonDetailSubService reasonDetailSubService) {
        ReasonDetailSubServiceResponse resp = ReasonDetailSubServiceResponse.builder()
                .reasonName(reasonDetailSubService.getReasonName())
                .id(reasonDetailSubService.getId())
                .price(reasonDetailSubService.getPrice())
                .build();
        return resp;
    }

    private StatusServiceResponse covertToStatusRequestResponse(StatusServiceRequest request) {
        StatusServiceResponse response = StatusServiceResponse.builder().build();
        response.setId(request.getId());
        response.setName(request.getRequestName());
        return response;
    }

    private RequestServiceResponse covertToRequestResponse(RequestService requestService) {
        RequestServiceResponse response = RequestServiceResponse.builder().build();
        if (Objects.isNull(requestService.getAccount())) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(apartment);


        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        response.setId(requestService.getId());
        response.setBlock(apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName());
        response.setServiceName(requestService.getReasonDetailSubService().getReasonName());
        response.setStatus(requestService.getStatusServiceRequest().getRequestName());
        response.setRoomName(apartment.getRoomNumber().getRoomName());
        response.setName(requestService.getAccount().getName());
        response.setDayWant(convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null));
        return response;
    }

    private RequestServiceClientResponse convertToHistoryResponse(RequestService requestService) {
        Date currentTime = new Date();
        Long diff = currentTime.getTime() - requestService.getDateRequest().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(Math.abs(diff));
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " ph?t tr??c";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " gi? tr??c";
        } else {
            time = convertDateToStringWithTimezone(requestService.getDateRequest(), DD_MM_YYYY, null);
        }
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        String serviceName = requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
        String firstDescription = "?? ??ng k? th?nh c?ng d?ch v? ";
        String serviceNameFirst = "S? d?ng d?ch v? ";
        String date = convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null);
        String timeStr = convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        response.setId(requestService.getId());
        response.setServiceName(serviceNameFirst + serviceName.toLowerCase() + " - " + date);
        response.setDescription(firstDescription + serviceName.toLowerCase() + " v?o l?c " + timeStr + " ng?y " + date);
        response.setTime(time);
        response.setStatusId(requestService.getStatusServiceRequest().getId());
        response.setTypeRequest(1L);
        response.setMinutes(minutes);
        return response;
    }

    private RequestServiceClientResponse convertRequestServiceToRequestServiceClientResponse(RequestService requestService) {
        String serviceName = requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
        Date currentTime = new Date();

        Account account = requestService.getAccount();
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(apartment);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        Long reasonRequestId = requestService.getReasonDetailSubService().getId();
        String serviceNameStr = "Y?u c?u s? d?ng d?ch v? " + serviceName.toLowerCase();
        String description;
        if (reasonRequestId >= 21 && reasonRequestId <= 29) {
            description = serviceNameStr + requestService.getReasonDetailSubService().getDetailSubService().getDetailSubServiceName().toLowerCase() + " " + requestService.getReasonDetailSubService().getReasonName().toLowerCase() + " t?i c?n h? " + roomNumber.getRoomName() + " l?c " + convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        } else {
            description = serviceNameStr + " t?i c?n h? " + roomNumber.getRoomName() + " l?c " + convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        }
        Long diff = currentTime.getTime() - requestService.getDateRequest().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " ph?t tr??c";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " gi? tr??c";
        } else {
            time = convertDateToStringWithTimezone(requestService.getDateRequest(), DD_MM_YYYY, null);
        }
        RequestServiceClientResponse response = RequestServiceClientResponse.builder()
                .id(requestService.getId())
                .serviceName(serviceNameStr + " - " + convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null))
                .description(description)
                .time(time)
                .statusId(requestService.getStatusServiceRequest().getId())
                .typeRequest(1L)
                .minutes(minutes)
                .build();
        return response;
    }

    private RequestServiceClientResponse convertVehicleCardToResponse(VehicleCard vehicleCard) {
        Account account = vehicleCard.getAccount();
        Date currentTime = new Date();
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(apartment);
        RoomNumber roomNumber = apartment.getRoomNumber();
        Long diff = currentTime.getTime() - vehicleCard.getStartDate().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " ph?t tr??c";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " gi? tr??c";
        } else {
            time = convertDateToStringWithTimezone(vehicleCard.getStartDate(), DD_MM_YYYY, null);
        }
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        response.setId(vehicleCard.getId());
        response.setServiceName("Y?u c?u ??ng k? th? g?i xe  - " + convertDateToStringWithTimezone(vehicleCard.getStartDate(), DD_MM_YYYY, null));
        response.setDescription("Y?u c?u ??ng k? th? g?i xe c?a c?n h? " + roomNumber.getRoomName() + " l?c " + convertDateToStringWithTimezone(vehicleCard.getStartDate(), HH_MM, null));
        response.setTime(time);
        response.setStatusId(vehicleCard.getStatusVehicleCard().getId());
        response.setTypeRequest(2L);
        response.setMinutes(minutes);
        return response;

    }

    private RequestServiceClientResponse convertHistoryResidentCard(ResidentCard residentCard) {
        Account account = residentCard.getAccount();
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        Date currentTime = new Date();
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(apartment);

        RoomNumber roomNumber = apartment.getRoomNumber();
        Long diff = currentTime.getTime() - residentCard.getStartDate().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " ph?t tr??c";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " gi? tr??c";
        } else {
            time = convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null);
        }
        response.setId(residentCard.getId());
        response.setServiceName("?? ??ng k? th?nh c?ng th?m th? c?n h?  - " + convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null));
        response.setDescription("?? ??ng k? th?nh c?ng th? g?i xe c?a c?n h? " + roomNumber.getRoomName() + " l?c " + convertDateToStringWithTimezone(residentCard.getStartDate(), HH_MM, null));
        response.setTime(time);
        response.setStatusId(residentCard.getStatusResidentCard().getId());
        response.setTypeRequest(3L);
        response.setMinutes(minutes);
        return response;
    }

}
