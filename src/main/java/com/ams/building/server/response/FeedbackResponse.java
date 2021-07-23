package com.ams.building.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FeedbackResponse {

    private Long feedbackId;
    private String description;
    private Integer star;
    private String name;
    private String createdDate;

}
