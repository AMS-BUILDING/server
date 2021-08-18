package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DetailSubServiceClientResponse {

    private Long id;
    private String detailSubServiceName;

}
