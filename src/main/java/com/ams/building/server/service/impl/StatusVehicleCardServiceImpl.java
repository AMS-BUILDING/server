package com.ams.building.server.service.impl;

import com.ams.building.server.bean.StatusVehicleCard;
import com.ams.building.server.dao.StatusVehicleCardDAO;
import com.ams.building.server.response.StatusVehicleCardResponse;
import com.ams.building.server.service.StatusVehicleCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class StatusVehicleCardServiceImpl implements StatusVehicleCardService {

    private static final Logger logger = Logger.getLogger(StatusVehicleCardServiceImpl.class);

    @Autowired
    private StatusVehicleCardDAO statusVehicleCardDAO;

    @Override
    public List<StatusVehicleCardResponse> listStatus() {
        List<StatusVehicleCard> parkingCards = statusVehicleCardDAO.findAll();
        List<StatusVehicleCardResponse> responses = new ArrayList<>();
        parkingCards.forEach(s -> responses.add(convertToStatusResponse(s)));
        return responses;
    }

    private StatusVehicleCardResponse convertToStatusResponse(StatusVehicleCard card) {
        StatusVehicleCardResponse response = StatusVehicleCardResponse.builder().build();
        response.setId(card.getId());
        response.setStatusName(card.getStatusName());
        return response;
    }
}
