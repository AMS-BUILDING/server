package com.ams.building.server.service;

import com.ams.building.server.response.RoomNumberResponse;

import java.util.List;

public interface RoomNumberService {

    List<RoomNumberResponse> roomNumberList(Long blockId, Long floorId);

}
