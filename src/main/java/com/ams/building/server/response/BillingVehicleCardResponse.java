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
public class BillingVehicleCardResponse {

    private Long vehicleCardId;
    private Long accountId;
    private Long vehicleId;
    private String blockName;
    private String roomNumber;
    private String vehicleTypeName;
    private Long quantity;
    private Double totalPrice;

}
