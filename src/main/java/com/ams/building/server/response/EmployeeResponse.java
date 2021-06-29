package com.ams.building.server.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String dob;
    private String identityCard;
    private String homeTown;
    private String currentAddress;
    private String gender;
    private String positionName;
}
