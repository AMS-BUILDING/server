package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccountResponse {

    private Long accountId;
    private Long apartmentId;
    private String name;
    private String phone;
    private String blockName;
    private String roomNumber;
    private Boolean gender;
    private String dob;
    private String email;
    private String homeTown;
    private String currentAddress;
    private String identifyCard;
    private String relationShip;

}
