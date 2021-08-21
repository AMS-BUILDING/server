package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Feedback;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.FeedbackDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.FeedbackResponse;
import com.ams.building.server.service.FeedbackService;
import com.ams.building.server.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDAO feedbackDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public void addFeedback(FeedbackRequest feedbackRequest) {
        if (Objects.isNull(feedbackRequest)) {
            throw new RestApiException(StatusCode.FEEDBACK_EMPTY);
        }
        if (StringUtils.isEmpty(feedbackRequest.getDescription().trim())) {
            throw new RestApiException(StatusCode.DESCRIPTION_EMPTY);
        }
        if (StringUtils.isEmpty(feedbackRequest.getStar())) {
            throw new RestApiException(StatusCode.STAR_EMPTY);
        }
        if (feedbackRequest.getStar() < 1 || feedbackRequest.getStar() > 5) {
            throw new RestApiException(StatusCode.STAR_FROM_ONE_TO_FIVE);
        }
        Account account = accountDAO.getAccountById(feedbackRequest.getAccountId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Feedback feedback = new Feedback();
        feedback.setDescription(feedbackRequest.getDescription());
        feedback.setStar(feedbackRequest.getStar());
        feedback.setAccount(account);
        feedbackDAO.save(feedback);
    }

    @Override
    public ApiResponse searchFeedbackByNameAndCreateDate(Integer page, Integer size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackDAO.findFeedbacksByName(name, pageable);
        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        feedbacks.forEach(feedback -> {
            feedbackResponseList.add(convertFeedbackToFeedbackDTO(feedback));
        });
        Long totalElement = feedbacks.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(feedbackResponseList).totalElement(totalElement).build();
        return response;
    }

    private FeedbackResponse convertFeedbackToFeedbackDTO(Feedback feedback) {
        FeedbackResponse feedbackResponse = FeedbackResponse.builder().build();
        feedbackResponse.setFeedbackId(feedback.getId());
        feedbackResponse.setName(feedback.getAccount().getName());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setStar(feedback.getStar());
        feedbackResponse.setCreatedDate(DateTimeUtils.convertDateToStringWithTimezone(feedback.getCreatedDate(), DateTimeUtils.DD_MM_YYYY, null));
        return feedbackResponse;
    }

}
