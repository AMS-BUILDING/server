package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusApartmentBilling;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.BillingApartmentTotalResponse;
import com.ams.building.server.service.impl.BillingApartmentServiceImpl;
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
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.HelperUtils.formatDoubleNUmber;

@RunWith(PowerMockRunner.class)
public class BillingApartmentServiceTest {

    @Mock
    ApartmentBillingDAO apartmentBillingDAO;

    @InjectMocks
    BillingApartmentServiceImpl billingApartmentService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void searchBuildingApartmentByMonth() {
        Pageable pageable = PageRequest.of(1, 5);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdf");
        List<ApartmentBilling> apartmentBillings = new ArrayList<>();
        apartmentBillings.add(new ApartmentBilling(1L, apartment, statusApartmentBilling, 0D, "07/2021", false));
        Page<ApartmentBilling> apartmentBillingPage = new PageImpl<>(apartmentBillings, pageable, apartmentBillings.size());
        Mockito.when(apartmentBillingDAO.searchApartmentBillingByMonth(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentBillingPage);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        billingApartmentService.searchBuildingApartmentByMonth(0, 5, "07/2021");
    }

    @Test
    public void searchBuildingApartmentByMonthTestFailByApartment() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        Pageable pageable = PageRequest.of(1, 5);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdf");
        List<ApartmentBilling> apartmentBillings = new ArrayList<>();
        apartmentBillings.add(new ApartmentBilling(1L, null, statusApartmentBilling, 0D, "07/2021", false));
        Page<ApartmentBilling> apartmentBillingPage = new PageImpl<>(apartmentBillings, pageable, apartmentBillings.size());
        Mockito.when(apartmentBillingDAO.searchApartmentBillingByMonth(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentBillingPage);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        billingApartmentService.searchBuildingApartmentByMonth(0, 5, "07/2021");
    }

    @Test
    public void searchBuildingApartmentByMonthTestFailByRoomNumberNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phòng không tồn tại");
        Pageable pageable = PageRequest.of(1, 5);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, null);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdf");
        List<ApartmentBilling> apartmentBillings = new ArrayList<>();
        apartmentBillings.add(new ApartmentBilling(1L, apartment, statusApartmentBilling, 0D, "07/2021", false));
        Page<ApartmentBilling> apartmentBillingPage = new PageImpl<>(apartmentBillings, pageable, apartmentBillings.size());
        Mockito.when(apartmentBillingDAO.searchApartmentBillingByMonth(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentBillingPage);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        billingApartmentService.searchBuildingApartmentByMonth(0, 5, "07/2021");
    }

    @Test
    public void searchBuildingApartmentByMonthFllorBlockNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tầng phòng không tồn tại");
        Pageable pageable = PageRequest.of(1, 5);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), null, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdf");
        List<ApartmentBilling> apartmentBillings = new ArrayList<>();
        apartmentBillings.add(new ApartmentBilling(1L, apartment, statusApartmentBilling, 0D, "07/2021", false));
        Page<ApartmentBilling> apartmentBillingPage = new PageImpl<>(apartmentBillings, pageable, apartmentBillings.size());
        Mockito.when(apartmentBillingDAO.searchApartmentBillingByMonth(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentBillingPage);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        billingApartmentService.searchBuildingApartmentByMonth(0, 5, "07/2021");
    }

    @Test
    public void searchBuildingApartmentByMonthBlockNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Block không tồn tại");
        Pageable pageable = PageRequest.of(1, 5);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, null, new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdf");
        List<ApartmentBilling> apartmentBillings = new ArrayList<>();
        apartmentBillings.add(new ApartmentBilling(1L, apartment, statusApartmentBilling, 0D, "07/2021", false));
        Page<ApartmentBilling> apartmentBillingPage = new PageImpl<>(apartmentBillings, pageable, apartmentBillings.size());
        Mockito.when(apartmentBillingDAO.searchApartmentBillingByMonth(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentBillingPage);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        billingApartmentService.searchBuildingApartmentByMonth(0, 5, "07/2021");
    }

    @Test
    public void searchBuildingApartmentByMonthStatusNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Trạng thái này không tồn tại");
        Pageable pageable = PageRequest.of(1, 5);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        StatusApartmentBilling statusApartmentBilling = new StatusApartmentBilling();
        statusApartmentBilling.setId(1L);
        statusApartmentBilling.setStatusName("sdf");
        List<ApartmentBilling> apartmentBillings = new ArrayList<>();
        apartmentBillings.add(new ApartmentBilling(1L, apartment, null, 0D, "07/2021", false));
        Page<ApartmentBilling> apartmentBillingPage = new PageImpl<>(apartmentBillings, pageable, apartmentBillings.size());
        Mockito.when(apartmentBillingDAO.searchApartmentBillingByMonth(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentBillingPage);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        billingApartmentService.searchBuildingApartmentByMonth(0, 5, "07/2021");
    }

    private BillingApartmentTotalResponse convertToBillingTotalResponse(ApartmentBilling billing) {
        BillingApartmentTotalResponse response = BillingApartmentTotalResponse.builder().build();
        Apartment apartment = billing.getApartment();
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        if (Objects.isNull(floorBlock)) {
            throw new RestApiException(StatusCode.FLOOR_BLOCK_NOT_EXIST);
        }
        Block block = floorBlock.getBlock();
        if (Objects.isNull(block)) {
            throw new RestApiException(StatusCode.BLOCK_NOT_EXIST);
        }
        StatusApartmentBilling status = billing.getStatusApartmentBilling();
        if (Objects.isNull(status)) {
            throw new RestApiException(StatusCode.STATUS_NOT_EXIST);
        }
        response.setId(billing.getId());
        response.setBlockName(block.getBlockName());
        response.setRoomNumber(roomNumber.getRoomName());
        response.setTotalPrice(formatDoubleNUmber(billing.getTotalPrice()));
        response.setBillingMonth(billing.getBillingMonth());
        response.setStatusName(status.getStatusName());
        return response;
    }
}
