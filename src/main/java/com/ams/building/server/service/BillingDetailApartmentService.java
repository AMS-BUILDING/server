package com.ams.building.server.service;

import com.ams.building.server.response.ApiResponse;

public interface BillingDetailApartmentService {

    ApiResponse searchBillingDetailAboutServiceByMonth(Integer page, Integer size, String month);

    ApiResponse searchBillingDetailAboutCardByMonth(Integer page, Integer size, String month);

    ApiResponse searchBillingDetailAboutResidentCardByAccountIdAndMonth(Integer page, Integer size, Long accountId, String month);

    ApiResponse searchBillingDetailAboutVehicleCardByAccountIdAndMonth(Integer page, Integer size, Long accountId, String month);

    void updateStatus(Long id, Long statusId);

}
