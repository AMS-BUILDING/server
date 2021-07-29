package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ForwardPasswordRequest {

    private String token;
    private String password;

}
