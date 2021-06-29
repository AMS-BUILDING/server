package com.ams.building.server.response;

import com.ams.building.server.dto.FeedbackDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ListFeedbackResponse {
    private Integer totalPage;
    private List<FeedbackDTO> feedbackResponses;
}
