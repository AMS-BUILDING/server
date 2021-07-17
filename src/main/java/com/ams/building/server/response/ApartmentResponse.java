package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApartmentResponse {

    private Long accountId;
    private String blockName;
    private String roomName;
    private String ownerName;

}
