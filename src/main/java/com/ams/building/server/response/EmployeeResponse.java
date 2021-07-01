package com.ams.building.server.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String identityCard;
    private String homeTown;
    private String currentAddress;
    private String gender;
    private String positionName;

}
