package com.ams.building.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceNameFeeApartmentResponse {

    private String serviceName;
    private Double feeService;

}
