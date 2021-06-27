package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ListAbsentResponse {

    private Integer totalPage;
    private List<AbsentResponse> absentResponses;

}
