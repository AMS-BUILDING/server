package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.BlockDAO;
import com.ams.building.server.dao.FloorBlockDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApartmentResponse;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.service.impl.ApartmentServiceImpl;
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
import java.util.List;
import java.util.Objects;

@RunWith(PowerMockRunner.class)
public class ApartmentServiceTest {

    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    @Mock
    private FloorBlockDAO floorBlockDAO;

    @Mock
    private ApartmentDAO apartmentDao;

    @Mock
    private BlockDAO blockDAO;

    @Mock
    private AccountDAO accountDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void apartmentListTestSuccess() {
        Pageable pageable = PageRequest.of(1, 5);
        List<ApartmentResponse> apartmentResponses = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        apartments.add(apartment);
        Page<Apartment> apartmentPage = new PageImpl<>(apartments, pageable, apartments.size());
        Mockito.when(apartmentDao.searchApartmentByRoomNumberHouseholderName(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentPage);
        for (Apartment a : apartments) {
            ApartmentResponse response = covertApartmentToDTO(a);
            apartmentResponses.add(response);
        }
        apartmentService.apartmentList("", "", 0, 5);
    }

    @Test
    public void export() {
        Pageable pageable = PageRequest.of(1, 5);
        List<Apartment> apartments = new ArrayList<>();
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        apartments.add(apartment);
        Page<Apartment> apartmentPage = new PageImpl<>(apartments, pageable, apartments.size());
        Mockito.when(apartmentDao.searchApartmentByRoomNumberHouseholderName(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentPage);
        HttpServletResponse response = new MockHttpServletResponse();
        apartmentService.exportApartmentList(response, "", "");
    }

    @Test
    public void exportAbsentDetailListTestFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Error Unknown");
        apartmentService.exportApartmentList(null, "", "");
    }

    @Test
    public void accountOfApartment() {
        Pageable pageable = PageRequest.of(1, 5);
        List<AccountResponse> residentResponses = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        apartments.add(apartment);
        Page<Apartment> apartmentPage = new PageImpl<>(apartments, pageable, apartments.size());
        Mockito.when(apartmentDao.searchResidentByNameRoomNumberAndPhone(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentPage);
        Mockito.when(apartmentDao.searchResidentByNameRoomNumber(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(apartmentPage);
        for (Apartment apartment1 : apartments) {
            AccountResponse response = convertApartmentToAccountResponse(apartment1);
            residentResponses.add(response);
        }
        apartmentService.accountOfApartment("", "", "", 0, 5);
    }

    @Test
    public void addOwnerToApartmentTestSuccess() {
        Long apartmentId = 1L;
        Long ownerId = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(new Account());
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(new Apartment());
        apartmentService.addOwnerToApartment(apartmentId, ownerId);
    }

    @Test
    public void exportAbsentDetailListTdsgfestFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long apartmentId = null;
        Long ownerId = null;
        apartmentService.addOwnerToApartment(apartmentId, ownerId);
    }

    @Test
    public void addListResidentToApartment() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long apartmentId = null;
        apartmentService.addListResidentToApartment(apartmentId, null);
    }

    @Test
    public void addListResidentToApartmdgfgent() {
        Long apartmentId = 1L;
        apartmentService.addListResidentToApartment(apartmentId, new ArrayList<>());
    }

    @Test
    public void exportAbsentDetailListTestdfgdfFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long apartmentId = 1L;
        Long ownerId = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(new Apartment());
        apartmentService.addOwnerToApartment(apartmentId, ownerId);
    }

    @Test
    public void exportAbsentDetailListTestdsfgsdFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        Long apartmentId = 1L;
        Long ownerId = 1L;
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(new Account());
        Mockito.when(apartmentDao.getApartmentById(Mockito.anyLong())).thenReturn(null);
        apartmentService.addOwnerToApartment(apartmentId, ownerId);
    }

    @Test
    public void blockListTestSuccess() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1L, "dsf"));
        Mockito.when(blockDAO.findAll()).thenReturn(blocks);
        List<BlockResponse> responses = new ArrayList<>();
        blocks.forEach(s -> responses.add(convertBlock(s)));
        apartmentService.blockList();
    }

    @Test
    public void floorList() {
        Mockito.when(floorBlockDAO.floorBlockByBlockId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        apartmentService.floorList(Mockito.anyLong());
    }

    @Test
    public void typeApartmentByAccountIdSuccess() {
        Long accountId = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong(), Mockito.any())).thenReturn(apartment);
        apartmentService.typeApartmentByAccountId(accountId);
    }


    private BlockResponse convertBlock(Block block) {
        if (Objects.isNull(block)) {
            throw new RestApiException(StatusCode.BLOCK_NOT_EXIST);
        }
        BlockResponse response = BlockResponse.builder().blockName(block.getBlockName()).id(block.getId()).build();
        return response;
    }

    private AccountResponse convertApartmentToAccountResponse(Apartment apartment) {
        Account account = apartment.getAccount();
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
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
        Position position = account.getPosition();
        AccountResponse response = AccountResponse.builder()
                .apartmentId(apartment.getId())
                .accountId(account.getId())
                .roomNumber(roomNumber.getRoomName())
                .blockName(block.getBlockName())
                .name(account.getName())
                .phone(account.getPhone())
                .dob(account.getDob())
                .gender(account.getGender())
                .identifyCard(account.getIdentifyCard())
                .homeTown(account.getHomeTown())
                .currentAddress(account.getCurrentAddress())
                .email(account.getEmail())
                .relationShip(position == null ? "Chủ hộ" : position.getName())
                .build();
        return response;
    }

    private ApartmentResponse covertApartmentToDTO(Apartment apartment) {
        Account account = apartment.getAccount();
        RoomNumber roomNumber = apartment.getRoomNumber();
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        Block block = floorBlock.getBlock();

        ApartmentResponse response = ApartmentResponse.builder()
                .apartmentId(apartment.getId())
                .roomNumberId(roomNumber.getId())
                .accountId(account.getId())
                .ownerName(account.getName())
                .blockName(block.getBlockName())
                .roomName(roomNumber.getRoomName())
                .build();
        return response;
    }

}
