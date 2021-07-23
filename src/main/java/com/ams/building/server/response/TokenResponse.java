package com.ams.building.server.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenResponse {

    private String accessToken;
    private Long expirationTime;

}
