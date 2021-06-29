package com.ams.building.server.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackResponse {
    private Long feedbackId;
    private String description;
    private String name;
    private String createdDate;
}
