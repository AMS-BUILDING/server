package com.ams.building.server.service;

import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequestConvert;
import com.ams.building.server.response.DashboardResponseTotalConvert;
import com.ams.building.server.response.DashboardTypeAccountResponseConvert;

import java.util.List;

public interface DashBoardService {

    DashboardResponse dashboardNumber();

    List<DashboardResponseTotalConvert> yearlyTotalRevenue();

    List<DashboardResponseTotalConvert> yearlyCountServiceRequest();

    List<DashboardResponseTotalConvert> monthlyAccount(String year);

    List<DashboardTypeAccountResponseConvert> typeApartmentAccount();

    List<DashboardResponseTotalConvert> monthlyVehicle();

    List<DashboardResponseNumberOfUseServiceRequestConvert> numberOfUseServiceRequest();

}
