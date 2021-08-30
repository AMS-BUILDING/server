package com.ams.building.server.service;

import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.DetailApartmentBillingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.BillingCardTotalResponse;
import com.ams.building.server.response.BillingResidentCardResponse;
import com.ams.building.server.response.BillingServiceResponse;
import com.ams.building.server.response.BillingVehicleCardResponse;
import com.ams.building.server.service.impl.BillingDetailApartmentServiceImpl;
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
import java.util.List;

@RunWith(PowerMockRunner.class)
public class BillingDetailApartmentServiceTest {

    @Mock
    ApartmentBillingDAO apartmentBillingDAO;

    @Mock
    DetailApartmentBillingDAO detailApartmentBillingDAO;

    @InjectMocks
    BillingDetailApartmentServiceImpl billingApartmentService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void searchBillingDetailAboutServiceByMonth() {
        Pageable pageable = PageRequest.of(1, 5);
        List<BillingServiceResponse> responses = new ArrayList<>();
        responses.add(new BillingServiceResponse());
        Page<BillingServiceResponse> apartmentBillingPage = new PageImpl<>(responses, pageable, responses.size());
        Mockito.when(detailApartmentBillingDAO.searchBillingDetailAboutServiceByMonth(Mockito.anyString(), Mockito.any())).thenReturn(apartmentBillingPage);
        billingApartmentService.searchBillingDetailAboutServiceByMonth(0, 5, "");
    }

    @Test
    public void searchBillingDetailAboutCardByMonth() {
        Pageable pageable = PageRequest.of(1, 5);
        List<BillingCardTotalResponse> responses = new ArrayList<>();
        responses.add(new BillingCardTotalResponse());
        Page<BillingCardTotalResponse> apartmentBillingPage = new PageImpl<>(responses, pageable, responses.size());
        Mockito.when(detailApartmentBillingDAO.searchBillingDetailAboutCardByMonth(Mockito.anyString(), Mockito.any())).thenReturn(apartmentBillingPage);
        billingApartmentService.searchBillingDetailAboutCardByMonth(0, 5, "");
    }

    @Test
    public void searchBillingDetailAboutResidentCardByAccountIdAndMonth() {
        Pageable pageable = PageRequest.of(1, 5);
        List<BillingResidentCardResponse> responses = new ArrayList<>();
        responses.add(new BillingResidentCardResponse());
        Page<BillingResidentCardResponse> apartmentBillingPage = new PageImpl<>(responses, pageable, responses.size());
        Mockito.when(detailApartmentBillingDAO.searchBillingDetailAboutResidentCardByAccountIdAndMonth(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(apartmentBillingPage);
        billingApartmentService.searchBillingDetailAboutResidentCardByAccountIdAndMonth(0, 5, 1L, "");
    }

    @Test
    public void searchBillingDetailAboutVehicleCardByAccountIdAndMonth() {
        Pageable pageable = PageRequest.of(1, 5);
        List<BillingVehicleCardResponse> responses = new ArrayList<>();
        responses.add(new BillingVehicleCardResponse());
        Page<BillingVehicleCardResponse> apartmentBillingPage = new PageImpl<>(responses, pageable, responses.size());
        Mockito.when(detailApartmentBillingDAO.searchBillingDetailAboutVehicleCardByAccountIdAndMonth(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(apartmentBillingPage);
        billingApartmentService.searchBillingDetailAboutVehicleCardByAccountIdAndMonth(0, 5, 1L, "");
    }

    @Test
    public void updateStatusSuccess() {
        Long id = 1L;
        Long statusId = 1L;
        ApartmentBilling billing = new ApartmentBilling();
        Mockito.when(apartmentBillingDAO.getDetailApartmentBilling(Mockito.anyLong())).thenReturn(billing);
        billingApartmentService.updateStatus(statusId, id);
    }

    @Test
    public void updateStatusFailIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Long statusId = 1L;
        billingApartmentService.updateStatus(statusId, id);
    }

    @Test
    public void updateStatusFailStatusIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long statusId = null;
        billingApartmentService.updateStatus(statusId, id);
    }

    @Test
    public void updateStatusFailBillingNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phí căn hộ của tháng này không có");
        Long id = 1L;
        Long statusId = 1L;
        Mockito.when(apartmentBillingDAO.getDetailApartmentBilling(Mockito.anyLong())).thenReturn(null);
        billingApartmentService.updateStatus(statusId, id);
    }
}
