package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleCardClientRequest {

    private Long vehicleId;
    private String vehicleName;
    private String vehicleBranch;
    private String licensePlate;
    private String vehicleColor;

}
