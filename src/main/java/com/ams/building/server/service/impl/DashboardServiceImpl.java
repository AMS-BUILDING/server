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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional
@Service
public class DashboardServiceImpl implements DashBoardService {

    private static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);

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
                .color(randomColor())
                .build();
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
                .color(randomColor())
                .build();
        return response;
    }

    private DashboardResponseTotalConvert dashboardResponseTotalToDashboardResponseTotalConvert(DashboardResponseTotal request) {
        DashboardResponseTotalConvert response = DashboardResponseTotalConvert.builder()
                .date(request.getDate())
                .total(request.getTotal())
                .color(randomColor())
                .build();
        return response;
    }

    private String randomColor() {
        Random ra = new Random();
        int r, g, b;
        r = ra.nextInt(255);
        g = ra.nextInt(255);
        b = ra.nextInt(255);
        Color color = new Color(r, g, b);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        hex = "#" + hex;
        return hex;
    }

}
