package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Feedback;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.FeedbackDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.FeedbackRequest;
import com.ams.building.server.response.FeedbackResponse;
import com.ams.building.server.service.impl.FeedbackServiceImpl;
import com.ams.building.server.utils.DateTimeUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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
    public void addFeedbackTestFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phản hồi không có dữ liệu");
        FeedbackRequest feedback = null;
        feedbackService.addFeedback(feedback);
    }

    @Test
    public void addFeedbackTestFailByDescriptionDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phần miêu tả không được để trống");
        FeedbackRequest feedback = FeedbackRequest.builder().build();
        feedback.setDescription("");
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        feedbackService.addFeedback(feedback);
    }

    @Test
    public void addFeedbackTestFailByStarEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Vui lòng chọn sao cho đánh giá của bạn");
        FeedbackRequest feedback = FeedbackRequest.builder().build();
        feedback.setDescription("ss");
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        feedbackService.addFeedback(feedback);
    }

    @Test
    public void addFeedbackTestFailByStartFrom() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số sao đánh giá phải trong phạm vi từ 1 tới 5");
        FeedbackRequest feedback = FeedbackRequest.builder().build();
        feedback.setDescription("aaa");
        feedback.setStar(6);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(account);
        feedbackService.addFeedback(feedback);
    }

    @Test
    public void addFeedbackTestFailByAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        FeedbackRequest feedback = FeedbackRequest.builder().build();
        feedback.setDescription("aa");
        feedback.setStar(3);
        Mockito.when(accountDAO.getAccountById(Mockito.anyLong())).thenReturn(null);
        feedbackService.addFeedback(feedback);
    }

    @Test
    public void searchFeedbackByNameAndCreateDate() {
        Mockito.when(feedbackDAO.findFeedbacksByName(Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(feedbacks);
        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        feedbacks.forEach(feedback -> {
            feedbackResponseList.add(convertFeedbackToFeedbackDTO(feedback));
        });
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
