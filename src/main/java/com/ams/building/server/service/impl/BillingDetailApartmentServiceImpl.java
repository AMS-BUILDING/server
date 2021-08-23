package com.ams.building.server.service.impl;

import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.DetailApartmentBillingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BillingCardTotalResponse;
import com.ams.building.server.response.BillingResidentCardResponse;
import com.ams.building.server.response.BillingServiceResponse;
import com.ams.building.server.response.BillingVehicleCardResponse;
import com.ams.building.server.service.BillingDetailApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class BillingDetailApartmentServiceImpl implements BillingDetailApartmentService {

    @Autowired
    private DetailApartmentBillingDAO detailApartmentBillingDAO;

    @Autowired
    private ApartmentBillingDAO apartmentBillingDAO;

    @Override
    public ApiResponse searchBillingDetailAboutServiceByMonth(Integer page, Integer size, String month) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BillingServiceResponse> serviceResponses = detailApartmentBillingDAO.searchBillingDetailAboutServiceByMonth(month, pageable);
        ApiResponse response = ApiResponse.builder().totalElement(serviceResponses.getTotalElements()).data(serviceResponses.toList()).build();
        return response;
    }

    @Override
    public ApiResponse searchBillingDetailAboutCardByMonth(Integer page, Integer size, String month) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BillingCardTotalResponse> cardTotalResponses = detailApartmentBillingDAO.searchBillingDetailAboutCardByMonth(month, pageable);
        ApiResponse response = ApiResponse.builder().totalElement(cardTotalResponses.getTotalElements()).data(cardTotalResponses.toList()).build();
        return response;
    }

    @Override
    public ApiResponse searchBillingDetailAboutResidentCardByAccountIdAndMonth(Integer page, Integer size, Long accountId, String month) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BillingResidentCardResponse> cardResponses = detailApartmentBillingDAO.searchBillingDetailAboutResidentCardByAccountIdAndMonth(accountId, month, pageable);
        ApiResponse response = ApiResponse.builder().totalElement(cardResponses.getTotalElements()).data(cardResponses.toList()).build();
        return response;
    }

    @Override
    public ApiResponse searchBillingDetailAboutVehicleCardByAccountIdAndMonth(Integer page, Integer size, Long accountId, String month) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BillingVehicleCardResponse> cardResponses = detailApartmentBillingDAO.searchBillingDetailAboutVehicleCardByAccountIdAndMonth(accountId, month, pageable);
        ApiResponse response = ApiResponse.builder().totalElement(cardResponses.getTotalElements()).data(cardResponses.toList()).build();
        return response;
    }

    @Override
    public void updateStatus(Long id, Long statusId) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(statusId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        ApartmentBilling billing = apartmentBillingDAO.getDetailApartmentBilling(id);
        if (Objects.isNull(billing)) {
            throw new RestApiException(StatusCode.APARTMENT_BILLING_NOT_EXIST);
        }
        apartmentBillingDAO.updateStatusBilling(statusId, id);
    }

}
