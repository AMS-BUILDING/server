package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApartmentOwnerRequest {

    private Long apartmentId;
    private String name;
    private Boolean gender;
    private String dob;
    private String email;
    private String phone;
    private String currentAddress;
    private String identifyCard;
    private String homeTown;

}
