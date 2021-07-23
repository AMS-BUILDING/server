package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResidentCardResponse {

    private Long residentId;
    private String residentCode;
    private String status;

}
