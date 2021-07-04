package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.DetailSubServiceResponse;
import com.ams.building.server.response.ServiceResponse;

import java.util.List;

public interface SubServiceService {

    List<ServiceResponse> serviceList();

    ApiResponse searchService(Integer page, Integer size, Long serviceId, String subServiceName);

    List<DetailSubServiceResponse> reasonDetailServiceBySubServiceId(Long subServiceId);
}
