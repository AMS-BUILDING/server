package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ResidentRequestWrap {

    private List<ResidentRequest> residentRequestList;
    private ApartmentOwnerRequest ownerRequest;
    private Long apartmentId;

}
