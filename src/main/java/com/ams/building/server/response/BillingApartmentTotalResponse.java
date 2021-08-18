package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BillingApartmentTotalResponse {

    private Long id;
    private String blockName;
    private String roomNumber;
    private String totalPrice;
    private String billingMonth;
    private String statusName;

}
