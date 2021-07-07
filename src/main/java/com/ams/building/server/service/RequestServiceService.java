package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.response.StatusServiceResponse;

import java.util.List;

public interface RequestServiceService {

    List<StatusServiceResponse> statusResponses();

    ApiResponse searchServiceRequest(String name, String serviceName, Long statusId, Integer size, Integer pageNo);

    RequestServiceResponse getRequestServiceById(Long id);

    void updateStatusRequest(Long statusId, Long requestId);

}
