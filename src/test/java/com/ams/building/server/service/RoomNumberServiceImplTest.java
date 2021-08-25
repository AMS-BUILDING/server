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
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.response.RoomNumberResponse;
import com.ams.building.server.service.impl.RoomNumberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class RoomNumberServiceImplTest {


    @Mock
    private ApartmentDAO apartmentDAO;

    @InjectMocks
    RoomNumberServiceImpl roomNumberServiceImpl;

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "ch? h?", true), "1213", true);

    Building building = new Building(1L, "A01", "aa", "cccc");

    FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));

    RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");

    Apartment apartment = new Apartment(1L, account, building, roomNumber);

    List<Apartment> apartments = Arrays.asList(apartment);


    @Test
    public void roomNumberList() {
        Mockito.when(apartmentDAO.searchRoomNumberByBlockAndFloorNullAccount(Mockito.any(), Mockito.any())).thenReturn(apartments);
        List<RoomNumberResponse> responses = new ArrayList<>();
        apartments.forEach(s -> responses.add(convertRoomNumberToDTO(s)));
        roomNumberServiceImpl.roomNumberList(1L, 1L);
    }


    private RoomNumberResponse convertRoomNumberToDTO(Apartment apartment) {
        RoomNumberResponse response = RoomNumberResponse.builder()
                .roomName(apartment.getRoomNumber().getRoomName())
                .id(apartment.getRoomNumber().getId())
                .build();
        return response;
    }

}
