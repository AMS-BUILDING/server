package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenResponse {

    private String accessToken;
    private Long expirationTime;
    private Long roleId;
    private Long accountId;

}
