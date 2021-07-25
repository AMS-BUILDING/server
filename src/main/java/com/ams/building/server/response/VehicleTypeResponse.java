package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleTypeResponse {

    private String vehicleName;
    private String vehicleBranch;
    private String seat;
    private String licensePlates;
    private String color;
    private String startDate;

}
