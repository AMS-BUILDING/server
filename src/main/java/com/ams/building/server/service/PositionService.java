package com.ams.building.server.service;

import com.ams.building.server.response.PositionResponse;

import java.util.List;

public interface PositionService {

    List<PositionResponse> positionByShow(Boolean show);

}
