package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UpdateResidentRequest {

    private Long accountId;
    private String name;
    private Boolean gender;
    private String dob;
    private String phone;
    private String email;
    private String identifyCard;
    private String homeTown;
    private String currentAddress;
}
