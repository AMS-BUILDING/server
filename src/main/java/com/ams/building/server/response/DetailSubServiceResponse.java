package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DetailSubServiceResponse {

    private String serviceName;
    private String subServiceName;
    private String detailSubServiceName;
    private String reasonName;
    private String price;

}
