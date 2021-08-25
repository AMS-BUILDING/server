package com.ams.building.server;

import com.ams.building.server.dao.*;
import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardResponseTotalConvert;
import com.ams.building.server.service.impl.DashboardServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)

public class DashboardServiceTest {

    @InjectMocks
    DashboardServiceImpl dashboardService;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ApartmentDAO apartmentDAO;

    @Autowired
    RequestServiceDAO requestServiceDAO;

    @Autowired
    DashboardRequestServiceDao dashboardRequestServiceDao;

    @Autowired
    DashboardAccountDao dashboardAccountDao;

    @Autowired
    DashboardVehicleCardDAO dashboardVehicleCardDAO;

    @Test
    public void dashboardNumber() {
        Mockito.when(accountDAO.countAccountEnable())
                .thenReturn(20L);
        Mockito.when(apartmentDAO.countEmptyApartment())
                .thenReturn(20L);
        Mockito.when(requestServiceDAO.totalRevenue())
                .thenReturn(20D);
        Mockito.when(requestServiceDAO.totalServiceRequest())
                .thenReturn(20L);

        DashboardResponse response = DashboardResponse.builder()
                .numberOfAccount(20L)
                .numberOfEmptyApartment(20L)
                .totalRevenue(20D)
                .totalServiceRequest(20L)
                .build();

        dashboardService.dashboardNumber();
    }


    List<DashboardResponseTotal> yearlyCountServiceRequest = Arrays.asList();

    @Test
    public void yearlyTotalRevenue() {
        List<DashboardResponseTotalConvert> response = new ArrayList<>();
        Mockito.when(dashboardRequestServiceDao.yearlyCountServiceRequest())
                .thenReturn(yearlyCountServiceRequest);

        for (DashboardResponseTotal r : yearlyCountServiceRequest) {
            response.add(dashboardResponseTotalToDashboardResponseTotalConvert(r));
        }

    }

    private DashboardResponseTotalConvert dashboardResponseTotalToDashboardResponseTotalConvert(DashboardResponseTotal request) {
        DashboardResponseTotalConvert response = DashboardResponseTotalConvert.builder()
                .date(request.getDate())
                .total(request.getTotal())
                .color("#37AE8D")
                .build();
        return response;
    }
}