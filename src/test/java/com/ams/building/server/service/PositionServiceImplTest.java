package com.ams.building.server.service;

import com.ams.building.server.bean.Position;
import com.ams.building.server.dao.PositionDAO;
import com.ams.building.server.response.PositionResponse;
import com.ams.building.server.service.impl.PositionServiceImpl;
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
public class PositionServiceImplTest {

    @Mock
    private PositionDAO positionDAO;

    @InjectMocks
    PositionServiceImpl positionServiceImpl;

    Position position = new Position(1L, "Chu HO", true);

    List<Position> positions = Arrays.asList(position);

    @Test
    public void positionByShow() {

        Mockito.when(positionDAO.positionsByShow(Mockito.anyBoolean())).thenReturn(positions);

        List<PositionResponse> responses = new ArrayList<>();
        positions.forEach(s -> responses.add(covertResponse(s)));

        positionServiceImpl.positionByShow(true);


    }

    private PositionResponse covertResponse(Position position) {
        PositionResponse response = PositionResponse.builder().id(position.getId()).name(position.getName()).build();
        return response;
    }
}
