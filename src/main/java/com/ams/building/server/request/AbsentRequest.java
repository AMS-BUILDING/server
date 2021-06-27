package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class AbsentRequest {

    private String name;
    private Date dob;
    private String identifyCard;
    private String homeTown;
    private String reason;
    private Date startDate;
    private Date endDate;
    private Long absentType;
    private Long accountDetailId;

}
