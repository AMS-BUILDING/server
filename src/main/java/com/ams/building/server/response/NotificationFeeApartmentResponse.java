package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class NotificationFeeApartmentResponse {

    private Long id;
    private String generalFeeName;
    private String feeGeneralFee;
    private String vehicleName;
    private String feeVehicleName;
    private String apartmentCardName;
    private String feeApartmentCard;
    private List<ServiceNameFeeApartmentResponse> services;
    private String billingMonth;
    private String status;
    private String total;

}
