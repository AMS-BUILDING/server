package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SubServiceResponse {

    private Long subSerivceId;
    private String serviceName;
    private String subServiceName;

}
