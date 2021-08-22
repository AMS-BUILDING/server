package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DashboardTypeAccountResponseConvert {

    private String type;
    private Long total;
    private String color;

}
