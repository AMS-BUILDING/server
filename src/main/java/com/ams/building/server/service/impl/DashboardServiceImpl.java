package com.ams.building.server.service.impl;

import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DashboardAccountDao;
import com.ams.building.server.dao.DashboardRequestServiceDao;
import com.ams.building.server.dao.DashboardVehicleCardDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequest;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequestConvert;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardResponseTotalConvert;
import com.ams.building.server.response.DashboardTypeAccountResponse;
import com.ams.building.server.response.DashboardTypeAccountResponseConvert;
import com.ams.building.server.service.DashBoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    DashboardVehicleCardDAO dashboardVehicleCardDAO;

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
    public List<DashboardResponseTotalConvert> yearlyTotalRevenue() {
        List<DashboardResponseTotal> yearlyTotalRevenue = dashboardRequestServiceDao.yearlyTotalRevenue();
        List<DashboardResponseTotalConvert> response = new ArrayList<>();
        for (DashboardResponseTotal r : yearlyTotalRevenue) {
            response.add(dashboardResponseTotalToDashboardResponseTotalConvert(r));
        }
        return response;
    }


    @Override
    public List<DashboardResponseTotalConvert> yearlyCountServiceRequest() {
        List<DashboardResponseTotal> yearlyCountServiceRequest = dashboardRequestServiceDao.yearlyCountServiceRequest();
        List<DashboardResponseTotalConvert> response = new ArrayList<>();
        for (DashboardResponseTotal r : yearlyCountServiceRequest) {
            response.add(dashboardResponseTotalToDashboardResponseTotalConvert(r));
        }
        return response;
    }

    @Override
    public List<DashboardResponseTotalConvert> monthlyAccount(String year) {
        List<DashboardResponseTotal> monthlyAccount = dashboardAccountDao.monthlyAccount(year);
        List<DashboardResponseTotalConvert> response = new ArrayList<>();
        for (DashboardResponseTotal r : monthlyAccount) {
            response.add(dashboardResponseTotalToDashboardResponseTotalConvert(r));
        }
        return response;
    }

    @Override
    public List<DashboardTypeAccountResponseConvert> typeApartmentAccount() {
        List<DashboardTypeAccountResponse> typeApartmentAccount = dashboardAccountDao.typeApartmentAccount();
        List<DashboardTypeAccountResponseConvert> response = new ArrayList<>();
        for (DashboardTypeAccountResponse r : typeApartmentAccount) {
            response.add(dashboardTypeAccountResponseToDashboardTypeAccountResponseConvert(r));
        }
        return response;
    }

    private DashboardTypeAccountResponseConvert dashboardTypeAccountResponseToDashboardTypeAccountResponseConvert(DashboardTypeAccountResponse request) {
        DashboardTypeAccountResponseConvert response = DashboardTypeAccountResponseConvert.builder()
                .type(request.getType())
                .total(request.getTotal())
                .build();
        if (request.getType().equalsIgnoreCase("65")) {
            response.setColor("#37AE8D");
        } else if (request.getType().equalsIgnoreCase("120")) {
            response.setColor("#275464");
        } else if (request.getType().equalsIgnoreCase("150")) {
            response.setColor("#8D7F96");
        } else if (request.getType().equalsIgnoreCase("200")) {
            response.setColor("#6F515B");
        } else {
            response.setColor("#028186");
        }
        return response;
    }

    @Override
    public List<DashboardResponseTotalConvert> monthlyVehicle() {
        List<DashboardResponseTotal> monthlyVehicle = dashboardVehicleCardDAO.monthlyVehicle();
        List<DashboardResponseTotalConvert> response = new ArrayList<>();
        for (DashboardResponseTotal r : monthlyVehicle) {
            response.add(dashboardResponseTotalToDashboardResponseTotalConvert(r));
        }
        return response;
    }

    @Override
    public List<DashboardResponseNumberOfUseServiceRequestConvert> numberOfUseServiceRequest() {
        List<DashboardResponseNumberOfUseServiceRequest> numberOfUseServiceRequest = dashboardRequestServiceDao.numberOfUseServiceRequest();
        List<DashboardResponseNumberOfUseServiceRequestConvert> response = new ArrayList<>();
        for (DashboardResponseNumberOfUseServiceRequest r : numberOfUseServiceRequest) {
            response.add(dashboardResponseNumberOfUseServiceRequestToDashboardResponseNumberOfUseServiceRequestConvert(r));
        }
        return response;
    }

    private DashboardResponseNumberOfUseServiceRequestConvert dashboardResponseNumberOfUseServiceRequestToDashboardResponseNumberOfUseServiceRequestConvert(DashboardResponseNumberOfUseServiceRequest request) {
        DashboardResponseNumberOfUseServiceRequestConvert response = DashboardResponseNumberOfUseServiceRequestConvert.builder()
                .serviceName(request.getServiceName())
                .total(request.getTotal())
                .color("#37AE8D")
                .build();
        return response;
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
