package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReasonDetailSubServiceResponse {

    private Long id;
    private String reasonName;
    private Double price;

}
