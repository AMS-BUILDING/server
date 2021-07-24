package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ManagerPersonResponse {

    private String name;
    private String phoneNumber;
    private String roleName;

}
