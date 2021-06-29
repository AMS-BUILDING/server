package com.ams.building.server.service;

import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.ListFeedbackResponse;

import java.util.Date;

public interface FeedbackService {
    void addFeedback(FeedbackRequest feedbackRequest);

    ListFeedbackResponse listAllFeedback(Integer size);

    ListFeedbackResponse searchFeedbackByNameAndCreateDate(Integer page, Integer size, String name, Date cratedDate);
}
