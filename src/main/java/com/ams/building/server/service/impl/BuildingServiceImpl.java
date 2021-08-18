package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.BuildingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.BuildingResponse;
import com.ams.building.server.service.BuildingService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BuildingServiceImpl implements BuildingService {

    private static final Logger logger = Logger.getLogger(BuildingServiceImpl.class);

    @Autowired
    private BuildingDAO buildingDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Override
    public BuildingResponse detailBuilding(Long id) {
        Building building = buildingDAO.getDetailById(1L);
        if (Objects.isNull(building)) {
            throw new RestApiException(StatusCode.BUILDING_NOT_EXIST);
        }
        Account account = accountDAO.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(id);
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
        BuildingResponse response = BuildingResponse.builder().build();
        response.setBuildingName(building.getBuildingName());
        response.setAddress(building.getAddress());
        response.setBlockName(block.getBlockName());
        response.setRoomName(roomNumber.getRoomName());
        logger.debug("detailBuilding: " + new Gson().toJson(response));
        return response;
    }

}
