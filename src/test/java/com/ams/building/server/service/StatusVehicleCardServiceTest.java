package com.ams.building.server.service;

import com.ams.building.server.bean.StatusVehicleCard;
import com.ams.building.server.dao.StatusVehicleCardDAO;
import com.ams.building.server.response.StatusVehicleCardResponse;
import com.ams.building.server.service.impl.StatusVehicleCardServiceImpl;
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
public class StatusVehicleCardServiceTest {


    @Mock
    private StatusVehicleCardDAO statusVehicleCardDAO;

    @InjectMocks
    StatusVehicleCardServiceImpl statusVehicleCardServiceImpl;

    StatusVehicleCard statusVehicleCard = new StatusVehicleCard(1L, "name");

    List<StatusVehicleCard> statusVehicleCards = Arrays.asList(statusVehicleCard);

    @Test
    public void listStatus() {

        Mockito.when(statusVehicleCardDAO.findAll()).thenReturn(statusVehicleCards);
        List<StatusVehicleCardResponse> responses = new ArrayList<>();
        statusVehicleCards.forEach(s -> responses.add(convertToStatusResponse(s)));
        statusVehicleCardServiceImpl.listStatus();
    }


    private StatusVehicleCardResponse convertToStatusResponse(StatusVehicleCard card) {
        StatusVehicleCardResponse response = StatusVehicleCardResponse.builder().build();
        response.setId(card.getId());
        response.setStatusName(card.getStatusName());
        return response;
    }
}
