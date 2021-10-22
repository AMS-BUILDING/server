package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;

import javax.servlet.http.HttpServletResponse;

public interface BillingApartmentService {

    ApiResponse searchBuildingApartmentByMonth(Integer page, Integer size, String month);

}
