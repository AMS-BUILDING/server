package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmployeeRequest {
    private String name;
    private Boolean gender;
    private String dob;
    private String phoneNumber;
    private String email;
    private String identifyCard;
    private String currentAddress;
    private String homeTown;
    private Long position;
}
