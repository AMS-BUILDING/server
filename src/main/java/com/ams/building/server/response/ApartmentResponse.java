package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApartmentResponse {

    private Long apartmentId;
    private Long accountId;
    private Long roomNumberId;
    private String blockName;
    private String roomName;
    private String ownerName;

}
