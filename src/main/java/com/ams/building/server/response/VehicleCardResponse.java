package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleCardResponse {

    private String vehicleOwner;
    private String phoneNumber;
    private String vehicleName;
    private String licensePlates;
    private String type;
    private String color;
    private String status;

}
