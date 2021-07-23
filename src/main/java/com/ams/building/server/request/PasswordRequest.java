package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PasswordRequest {

    private String newPassword;
    private String confirmPassword;

}
