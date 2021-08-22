package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Position;
import com.ams.building.server.dao.PositionDAO;
import com.ams.building.server.response.PositionResponse;
import com.ams.building.server.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionDAO positionDAO;

    @Override
    public List<PositionResponse> positionByShow(Boolean show) {
        List<Position> positions = positionDAO.positionsByShow(show);
        List<PositionResponse> responses = new ArrayList<>();
        positions.forEach(s -> responses.add(covertResponse(s)));
        return responses;
    }

    private PositionResponse covertResponse(Position position) {
        PositionResponse response = PositionResponse.builder().id(position.getId()).name(position.getName()).build();
        return response;
    }

}
