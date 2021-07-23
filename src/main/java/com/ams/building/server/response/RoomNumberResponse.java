package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RoomNumberResponse {

    private Long apartmentId;
    private Long id;
    private String roomName;

}
