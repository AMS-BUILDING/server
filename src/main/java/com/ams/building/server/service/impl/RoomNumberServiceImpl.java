package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Apartment;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.response.RoomNumberResponse;
import com.ams.building.server.service.RoomNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomNumberServiceImpl implements RoomNumberService {

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Override
    public List<RoomNumberResponse> roomNumberList(Long blockId, Long floorId) {
        List<Apartment> apartments = apartmentDAO.searchRoomNumberByBlockAndFloorNullAccount(blockId, floorId);
        List<RoomNumberResponse> responses = new ArrayList<>();
        apartments.forEach(s -> responses.add(convertRoomNumberToDTO(s)));
        return responses;
    }

    private RoomNumberResponse convertRoomNumberToDTO(Apartment apartment) {
        RoomNumberResponse response = RoomNumberResponse.builder()
                .roomName(apartment.getRoomNumber().getRoomName())
                .id(apartment.getRoomNumber().getId())
                .build();
        return response;
    }
}
