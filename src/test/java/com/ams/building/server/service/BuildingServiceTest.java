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
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.BuildingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.service.impl.BuildingServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class BuildingServiceTest {
    @Mock
    BuildingDAO buildingDAO;

    @Mock
    AccountDAO accountDAO;

    @Mock
    ApartmentDAO apartmentDAO;

    @InjectMocks
    BuildingServiceImpl buildingService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void detailBuildingTestSuccess() {
        Long id = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(building);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        buildingService.detailBuilding(id);
    }

    @Test
    public void detailBuildingTestFailBuildingNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tòa nhà không tồn tại");
        Long id = 1L;
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(null);
        buildingService.detailBuilding(id);
    }

    @Test
    public void detailBuildingTestFailAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        Long id = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(building);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        buildingService.detailBuilding(id);
    }

    @Test
    public void detailBuildingTestFailApartmentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        Long id = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(building);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(null);
        buildingService.detailBuilding(id);
    }

    @Test
    public void detailBuildingTestFailRoomNumberNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phòng không tồn tại");
        Long id = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, null);
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(building);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        buildingService.detailBuilding(id);
    }

    @Test
    public void detailBuildingTestFailFloorBlockNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tầng phòng không tồn tại");
        Long id = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), null, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(building);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        buildingService.detailBuilding(id);
    }

    @Test
    public void detailBuildingTestFailBlockNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Block không tồn tại");
        Long id = 1L;
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, null, new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(buildingDAO.getDetailById(Mockito.anyLong())).thenReturn(building);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.anyLong())).thenReturn(apartment);
        buildingService.detailBuilding(id);
    }

}
