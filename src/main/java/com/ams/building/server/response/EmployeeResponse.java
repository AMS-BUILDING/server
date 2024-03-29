package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String dob;
    private String identifyCard;
    private String homeTown;
    private String currentAddress;
    private Boolean gender;
    private String positionName;

}
