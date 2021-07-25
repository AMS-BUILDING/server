package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.VehicleCardResponse;
import com.ams.building.server.response.VehicleTypeResponse;

import java.util.List;

public interface VehicleCardService {

    ApiResponse searchVehicleCard(Integer page, Integer size, String vehicleOwner, String phoneNumber, String licenesPlates, Long statusId);

    VehicleCardResponse detailVehicleCard(Long id);

    void updateStatusVehicleCard(Long id, Long statusId);

    void removeVehicleCard(Long id);

    List<VehicleTypeResponse> listVehicleByTypeAndByAccountId(Long id, Long vehicleTypeId);

}
