package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.response.StatusServiceResponse;

import java.util.List;

public interface RequestServiceService {

    List<StatusServiceResponse> statusResponses();

    ApiResponse searchServiceRequest(Integer page,Integer size, String name, String serviceName, Long statusId);

    RequestServiceResponse getRequestServiceById(Long id);

    void updateStatusRequest(Long statusId, Long requestId);

}
