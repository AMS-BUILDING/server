package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestServiceRequest {

    private Long reasonDetailSubServiceId;
    private Long accountId;
    private String startDate;
    private String endDate;

}
