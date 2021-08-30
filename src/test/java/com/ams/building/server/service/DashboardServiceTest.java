package com.ams.building.server.service;

import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DashboardAccountDAO;
import com.ams.building.server.dao.DashboardRequestServiceDAO;
import com.ams.building.server.dao.DashboardVehicleCardDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequest;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardTypeAccountResponse;
import com.ams.building.server.service.impl.DashboardServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)

public class DashboardServiceTest {

    @InjectMocks
    DashboardServiceImpl dashboardService;

    @Mock
    AccountDAO accountDAO;

    @Mock
    ApartmentDAO apartmentDAO;

    @Mock
    RequestServiceDAO requestServiceDAO;

    @Mock
    DashboardRequestServiceDAO dashboardRequestServiceDao;

    @Mock
    DashboardAccountDAO dashboardAccountDao;

    @Mock
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
        dashboardService.dashboardNumber();
    }

    @Test
    public void dashboardNumberSuccessWithTotal() {
        Mockito.when(accountDAO.countAccountEnable())
                .thenReturn(20L);
        Mockito.when(apartmentDAO.countEmptyApartment())
                .thenReturn(20L);
        Mockito.when(requestServiceDAO.totalRevenue())
                .thenReturn(null);
        Mockito.when(requestServiceDAO.totalServiceRequest())
                .thenReturn(20L);
        dashboardService.dashboardNumber();
    }

    @Test
    public void yearlyTotalRevenue() {
        List<DashboardResponseTotal> yearlyCountServiceRequest = new ArrayList<>();
        Mockito.when(dashboardRequestServiceDao.yearlyCountServiceRequest())
                .thenReturn(yearlyCountServiceRequest);
        dashboardService.yearlyTotalRevenue();
    }

    @Test
    public void yearlyCountServiceRequest() {
        List<DashboardResponseTotal> yearlyCountServiceRequest = new ArrayList<>();
        Mockito.when(dashboardRequestServiceDao.yearlyCountServiceRequest())
                .thenReturn(yearlyCountServiceRequest);
        dashboardService.yearlyCountServiceRequest();
    }

    @Test
    public void monthlyAccount() {
        List<DashboardResponseTotal> yearlyCountServiceRequest = new ArrayList<>();
        Mockito.when(dashboardAccountDao.monthlyAccount(Mockito.anyString()))
                .thenReturn(yearlyCountServiceRequest);
        dashboardService.monthlyAccount("2021");
    }

    @Test
    public void typeApartmentAccount() {
        List<DashboardTypeAccountResponse> yearlyCountServiceRequest = new ArrayList<>();
        Mockito.when(dashboardAccountDao.typeApartmentAccount())
                .thenReturn(yearlyCountServiceRequest);
        dashboardService.typeApartmentAccount();
    }

    @Test
    public void monthlyVehicle() {
        List<DashboardResponseTotal> yearlyCountServiceRequest = new ArrayList<>();
        Mockito.when(dashboardVehicleCardDAO.monthlyVehicle())
                .thenReturn(yearlyCountServiceRequest);
        dashboardService.monthlyVehicle();
    }

    @Test
    public void numberOfUseServiceRequest() {
        List<DashboardResponseNumberOfUseServiceRequest> yearlyCountServiceRequest = new ArrayList<>();
        Mockito.when(dashboardRequestServiceDao.numberOfUseServiceRequest())
                .thenReturn(yearlyCountServiceRequest);
        dashboardService.numberOfUseServiceRequest();
    }

}
