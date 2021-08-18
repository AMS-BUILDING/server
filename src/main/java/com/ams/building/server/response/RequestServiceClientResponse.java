package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestServiceClientResponse {

    private Long id;
    private String serviceName;
    private String description;
    private String time;
    private Long statusId;

}
