package com.ams.building.server.service;

import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.ApiResponse;

public interface FeedbackService {

    void addFeedback(FeedbackRequest feedbackRequest);

    ApiResponse searchFeedbackByNameAndCreateDate(Integer page, Integer size, String name);

}
