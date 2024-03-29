package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AResidentRequest {

    private Long apartmentId;
    private ResidentRequest request;

}
