package com.ams.building.server.service.impl;

import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DashboardAccountDao;
import com.ams.building.server.dao.DashboardRequestServiceDao;
import com.ams.building.server.dao.DashboardVehicleCardDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequest;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardTypeAccountResponse;
import com.ams.building.server.service.DashBoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DashboardServiceImpl implements DashBoardService {

    private static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private RequestServiceDAO requestServiceDAO;

    @Autowired
    private DashboardRequestServiceDao dashboardRequestServiceDao;

    @Autowired
    private DashboardAccountDao dashboardAccountDao;

    @Autowired
    private DashboardVehicleCardDAO dashboardVehicleCardDAO;

    @Override
    public DashboardResponse dashboardNumber() {
        DashboardResponse response = DashboardResponse.builder()
                .numberOfAccount(accountDAO.countAccountEnable())
                .numberOfEmptyApartment(apartmentDAO.countEmptyApartment())
                .totalRevenue(requestServiceDAO.totalRevenue())
                .totalServiceRequest(requestServiceDAO.totalServiceRequest())
                .build();
        return response;
    }

    @Override
    public List<DashboardResponseTotal> yearlyTotalRevenue() {
        List<DashboardResponseTotal> response = dashboardRequestServiceDao.yearlyTotalRevenue();
        return response;
    }

    @Override
    public List<DashboardResponseTotal> yearlyCountServiceRequest() {
        List<DashboardResponseTotal> response = dashboardRequestServiceDao.yearlyCountServiceRequest();
        return response;
    }

    @Override
    public List<DashboardResponseTotal> monthlyAccount(String year) {
        List<DashboardResponseTotal> response = dashboardAccountDao.monthlyAccount(year);
        return response;
    }

    @Override
    public List<DashboardTypeAccountResponse> typeApartmentAccount() {
        List<DashboardTypeAccountResponse> response = dashboardAccountDao.typeApartmentAccount();
        return response;
    }

    @Override
    public List<DashboardResponseTotal> monthlyVehicle() {
        List<DashboardResponseTotal> response = dashboardVehicleCardDAO.monthlyVehicle();
        return response;
    }

    @Override
    public List<DashboardResponseNumberOfUseServiceRequest> numberOfUseServiceRequest() {
        List<DashboardResponseNumberOfUseServiceRequest> response = dashboardRequestServiceDao.numberOfUseServiceRequest();
        return response;
    }

}
