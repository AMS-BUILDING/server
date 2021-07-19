package com.ams.building.server.service;

import com.ams.building.server.response.AccountDetailResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.response.FloorResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ApartmentService {

   ApiResponse apartmentList(String roomName,String householderName, Integer page, Integer size);

    void exportApartmentList(HttpServletResponse response,String roomName,String householderName);

    void addOwnerToApartment(Long apartmentId , Long ownerId);

    void addListResidentToApartment(Long apartmentId , List<Long> residentId);

    ApiResponse accountOfApartment(String name , String roomNumber , String phone , Integer page, Integer size);

    AccountDetailResponse getAccountDetail( Long accountId,Long apartmentId );

    List<Long> disableApartment(Long id);

    List<BlockResponse> blockList();

    List<FloorResponse> floorList();

}
