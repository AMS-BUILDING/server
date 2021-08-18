package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResidentRequest {

    private String name;
    private String identifyCard;
    private String phone;
    private String email;
    private Boolean gender;
    private String dob;
    private Long positionId;
    private String currentAddress;
    private String homeTown;

}
