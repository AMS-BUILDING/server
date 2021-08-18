package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DashboardResponse {

    private Long numberOfEmptyApartment;
    private Long numberOfAccount;
    private Double totalRevenue;
    private Long totalServiceRequest;

}
