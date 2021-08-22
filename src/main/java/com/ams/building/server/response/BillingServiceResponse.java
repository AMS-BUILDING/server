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
public class BillingServiceResponse {

    private Long id;
    private String blockName;
    private String roomNumber;
    private String subServiceName;
    private Double subServicePrice;
    private String billingMonth;

}
