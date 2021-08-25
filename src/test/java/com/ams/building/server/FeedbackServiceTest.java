package com.ams.building.server;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Feedback;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.FeedbackDAO;
import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.FeedbackResponse;
import com.ams.building.server.service.impl.FeedbackServiceImpl;
import com.ams.building.server.utils.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class FeedbackServiceTest {
    @InjectMocks
    FeedbackServiceImpl feedbackService;
    @Mock
    private FeedbackDAO feedbackDAO;
    @Mock
    private AccountDAO accountDAO;

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "chủ hộ", true), "1213", true);

    FeedbackRequest feedbackRequest = FeedbackRequest.builder().accountId(1L).description("description").star(5).build();

    Pageable pageable = PageRequest.of(1, 5);

    Feedback feedback = new Feedback(1L, account, "abc", 1, new Date());

    List<Feedback> feedbackList = Arrays.asList(feedback);

    Page<Feedback> feedbacks = new PageImpl<>(feedbackList, pageable, feedbackList.size());

    @Test
    public void addFeedback() {

        Mockito.when(accountDAO.getAccountById(feedbackRequest.getAccountId()))
                .thenReturn(account);

        Feedback feedback = new Feedback();
        feedback.setDescription("Description");
        feedback.setStar(Mockito.anyInt());
        feedback.setAccount(account);

        Mockito.when(feedbackDAO.save(feedback))
                .thenReturn(feedback);

        feedbackService.addFeedback(feedbackRequest);
    }

    @Test
    public void searchFeedbackByNameAndCreateDate() {
        Pageable pageable = PageRequest.of(1, 5);

        Mockito.when(feedbackDAO.findFeedbacksByName(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(feedbacks);

        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        feedbacks.forEach(feedback -> {
            feedbackResponseList.add(convertFeedbackToFeedbackDTO(feedback));
        });

        Long totalElement = feedbacks.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(feedbackResponseList).totalElement(totalElement).build();
        feedbackService.searchFeedbackByNameAndCreateDate(1, 5, "name");
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
