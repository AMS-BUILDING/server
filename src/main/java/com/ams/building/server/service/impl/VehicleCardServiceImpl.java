package com.ams.building.server.service.impl;

import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.VehicleCardDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.VehicleCardResponse;
import com.ams.building.server.service.VehicleCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VehicleCardServiceImpl implements VehicleCardService {

    private static final Logger logger = Logger.getLogger(VehicleCardServiceImpl.class);

    @Autowired
    private VehicleCardDAO vehicleCardDAO;

    @Override
    public ApiResponse searchVehicleCard(Integer page, Integer size, String vehicleOwner, String phoneNumber, String licenesPlates, Long statusId) {
        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleCard> vehicleCards;
        if (statusId == -1) {
            vehicleCards = vehicleCardDAO.vehicleCardListWithoutStatus(vehicleOwner, phoneNumber, licenesPlates, pageable);
        } else {
            vehicleCards = vehicleCardDAO.vehicleCardListWithStatus(vehicleOwner, phoneNumber, licenesPlates, statusId, pageable);
        }
        for (VehicleCard ad : vehicleCards) {
            VehicleCardResponse response = convertToCardResponse(ad);
            vehicleCardResponses.add(response);
        }
        Long totalElement = vehicleCards.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(vehicleCardResponses).totalElement(totalElement).build();
        return response;
    }

    @Override
    public VehicleCardResponse detailVehicleCard(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        VehicleCard card = vehicleCardDAO.getVehicleCardById(id);
        if (Objects.isNull(card)) {
            throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
        }
        VehicleCardResponse response = convertToCardResponse(card);
        return response;
    }

    @Override
    public void updateStatusVehicleCard(Long id, Long statusId) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        VehicleCard card = vehicleCardDAO.getVehicleCardById(id);
        if (Objects.isNull(card)) {
            throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
        }
        vehicleCardDAO.updateStatus(statusId, id);
    }

    @Override
    public void removeVehicleCard(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        VehicleCard card = vehicleCardDAO.getVehicleCardById(id);
        if (Objects.isNull(card)) {
            throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
        }
        vehicleCardDAO.delete(card);
    }

    private VehicleCardResponse convertToCardResponse(VehicleCard card) {
        VehicleCardResponse response = VehicleCardResponse.builder().build();
        response.setVehicleOwner(card.getAccount().getName());
        response.setPhoneNumber(card.getAccount().getPhone());
        response.setVehicleName(card.getVehicleName());
        response.setLicensePlates(card.getLicensePlate());
        response.setType(card.getVehicle().getVehicleName());
        response.setColor(card.getVehicleColor());
        response.setStatus(card.getStatusVehicleCard().getStatusName());
        return response;
    }

}
