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
public class BillingCardTotalResponse {

    private Long id;
    private Long accountId;
    private String blockName;
    private String roomNumber;
    private Double totalPriceVehicleCard;
    private Double totalPriceResidentCard;
    private String billingMonth;

}
