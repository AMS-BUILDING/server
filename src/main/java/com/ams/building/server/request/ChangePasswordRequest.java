package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePasswordRequest {

    private Long id;
    private String password;
    private String newPassword;
}
