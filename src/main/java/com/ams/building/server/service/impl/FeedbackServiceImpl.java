package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Feedback;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.FeedbackDAO;
import com.ams.building.server.dto.FeedbackDTO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.ListFeedbackResponse;
import com.ams.building.server.service.FeedbackService;
import com.ams.building.server.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        if (feedbackRequest.getDescription().isEmpty()) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(feedbackRequest.getAccountDetailId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Feedback feedback = new Feedback();
        feedback.setDescription(feedbackRequest.getDescription());
        feedback.setCreatedDate(new Date());
        feedback.setAccount(account);
        feedbackDAO.save(feedback);
    }

    @Override
    public ListFeedbackResponse listAllFeedback(Integer size) {
        List<Feedback> feedbacks = feedbackDAO.findAll();
        List<FeedbackDTO> feedbackDTOList = new ArrayList<>();
        feedbacks.forEach(feedback -> {
            feedbackDTOList.add(convertFeedbackToFeedbackDTO(feedback));
        });

        Integer totalPage = feedbacks.size() % size == 0 ? feedbacks.size() / size : feedbacks.size() / size + 1;
        ListFeedbackResponse response = ListFeedbackResponse.builder().feedbackResponses(feedbackDTOList).totalPage(totalPage).build();
        return response;
    }

    @Override
    public ListFeedbackResponse searchFeedbackByNameAndCreateDate(Integer page, Integer size, String name, Date crateDate) {
        Page<Feedback> feedbacks;
        Pageable pageable = PageRequest.of(page, size);
        List<FeedbackDTO> feedbackDTOList = new ArrayList<>();
        if (Objects.isNull(crateDate)) {
            feedbacks = feedbackDAO.findFeedbacksByName(name, pageable);
        } else {
            feedbacks = feedbackDAO.findFeedbacksByNameAndCreateDate(name, crateDate, pageable);
        }
        feedbacks.forEach(feedback -> {
            feedbackDTOList.add(convertFeedbackToFeedbackDTO(feedback));
        });
        Integer totalPage = feedbacks.getTotalPages();
        ListFeedbackResponse response = ListFeedbackResponse.builder().feedbackResponses(feedbackDTOList).totalPage(totalPage).build();
        return response;
    }

    private FeedbackDTO convertFeedbackToFeedbackDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackId(feedback.getId());
        feedbackDTO.setName(feedback.getAccount().getName());
        feedbackDTO.setDescription(feedback.getDescription());
        feedbackDTO.setCreatedDate(DateTimeUtils.convertDateToStringWithTimezone(feedback.getCreatedDate(),DateTimeUtils.DD_MM_YYYY,null));
        return feedbackDTO;
    }
}
