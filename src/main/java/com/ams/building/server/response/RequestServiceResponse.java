package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestServiceResponse {

    private Long id;
    private String name;
    private String block;
    private String roomName;
    private String serviceName;
    private String status;
    
}
