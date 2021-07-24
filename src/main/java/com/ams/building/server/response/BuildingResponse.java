package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BuildingResponse {

    private String buildingName;
    private String blockName;
    private String roomName;
    private String address;

}
