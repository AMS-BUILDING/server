package com.ams.building.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private Long feedbackId;
    private String description;
    private String name;
    private String createdDate;
}
