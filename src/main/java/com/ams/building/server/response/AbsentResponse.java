package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AbsentResponse {

    private Long absentDetailId;
    private String name;
    private String identifyCard;
    private String homeTown;
    private String block;
    private String roomNumber;
    private String startDate;
    private String endDate;
    private String absentType;
    private String reason;

}
