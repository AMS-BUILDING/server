package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequestConvert;
import com.ams.building.server.response.DashboardResponseTotalConvert;
import com.ams.building.server.response.DashboardTypeAccountResponseConvert;
import com.ams.building.server.service.DashBoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class DashboardController {

    private static final Logger logger = Logger.getLogger(DashboardController.class);

    @Autowired
    DashBoardService dashBoardService;


    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_NUMBER)
    public ResponseEntity<?> dashboardNumber() {
        DashboardResponse dashboardResponse = dashBoardService.dashboardNumber();
        ResponseEntity<DashboardResponse> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_YEARLY_TOTAL_REVENUE)
    public ResponseEntity<?> yearlyTotalRevenue() {
        List<DashboardResponseTotalConvert> dashboardResponse = dashBoardService.yearlyTotalRevenue();
        ResponseEntity<List<DashboardResponseTotalConvert>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_YEARLY_COUNT_SERVICE_REQUEST)
    public ResponseEntity<?> yearlyCountServiceRequest() {
        List<DashboardResponseTotalConvert> dashboardResponse = dashBoardService.yearlyCountServiceRequest();
        ResponseEntity<List<DashboardResponseTotalConvert>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_MONTHLY_ACCOUNT)
    public ResponseEntity<?> monthlyAccount(@RequestParam String year) {
        List<DashboardResponseTotalConvert> dashboardResponse = dashBoardService.monthlyAccount(year);
        ResponseEntity<List<DashboardResponseTotalConvert>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_TYPE_APARTMENT_ACCOUNT)
    public ResponseEntity<?> typeApartmentAccount() {
        List<DashboardTypeAccountResponseConvert> dashboardResponse = dashBoardService.typeApartmentAccount();
        ResponseEntity<List<DashboardTypeAccountResponseConvert>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_MONTHLY_VEHICLE)
    public ResponseEntity<?> monthlyVehicle() {
        List<DashboardResponseTotalConvert> dashboardResponse = dashBoardService.monthlyVehicle();
        ResponseEntity<List<DashboardResponseTotalConvert>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_NUMBER_OF_USER_SERVICE_REQUEST)
    public ResponseEntity<?> numberOfUseServiceRequest() {
        List<DashboardResponseNumberOfUseServiceRequestConvert> dashboardResponse = dashBoardService.numberOfUseServiceRequest();
        ResponseEntity<List<DashboardResponseNumberOfUseServiceRequestConvert>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

}
