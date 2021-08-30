package com.ams.building.server.service;


import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.response.FloorResponse;
import com.ams.building.server.response.RoomNumberResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ApartmentService {

    ApiResponse apartmentList(String roomName, String householderName, Integer page, Integer size);

    void exportApartmentList(HttpServletResponse response, String roomName, String householderName);

    void addOwnerToApartment(Long apartmentId, Long ownerId);

    void addListResidentToApartment(Long apartmentId, List<Long> residentId);

    ApiResponse accountOfApartment(String name, String roomNumber, String phone, Integer page, Integer size);

    List<BlockResponse> blockList();

    List<FloorResponse> floorList(Long blockId);

    String typeApartmentByAccountId(Long accountId);

    List<RoomNumberResponse> roomNumberList(Long blockId, Long floorId);

    List<AccountResponse> dependentPerson(Long id);

    void addResidentToApartment(Long apartmentId, ResidentRequest request);

}
