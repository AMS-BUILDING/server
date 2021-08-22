package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.ResidentCardAddResponse;

public interface ResidentCardService {

    ApiResponse getResidentCardByAccountId(Integer page, Integer size, Long accountId);

    void updateStatusResidentCard(Long cardId, Long statusId);

    void removeResidentCard(Long id);

    void addResidentCard(String email);

    ResidentCardAddResponse addResidentCard(Long amount, Long accountId);

}
