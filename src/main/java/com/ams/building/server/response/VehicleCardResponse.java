package com.ams.building.server.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCardResponse {

    private String vehicleOwner;
    private String phoneNumber;
    private String vehicleName;
    private String licensePlates;
    private String type;
    private String color;
    private String status;

}
