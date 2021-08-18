package com.ams.building.server.service;

import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequest;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardTypeAccountResponse;

import java.util.List;

public interface DashBoardService {

    DashboardResponse dashboardNumber();

    List<DashboardResponseTotal> yearlyTotalRevenue();

    List<DashboardResponseTotal> yearlyCountServiceRequest();

    List<DashboardResponseTotal> monthlyAccount(String year);

    List<DashboardTypeAccountResponse> typeApartmentAccount();

    List<DashboardResponseTotal> monthlyVehicle();

    List<DashboardResponseNumberOfUseServiceRequest> numberOfUseServiceRequest();

}
