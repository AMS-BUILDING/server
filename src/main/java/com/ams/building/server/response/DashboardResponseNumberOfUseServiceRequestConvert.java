package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DashboardResponseNumberOfUseServiceRequestConvert {

    private String serviceName;
    private Long total;
    private String color;

}
