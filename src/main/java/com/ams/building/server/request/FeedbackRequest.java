package com.ams.building.server.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FeedbackRequest {

    private Long accountId;
    private Integer star;
    private String description;

}
