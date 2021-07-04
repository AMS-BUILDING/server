package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ServiceRequest {

    private Integer serviceId;
    private String subServiceName;

}
