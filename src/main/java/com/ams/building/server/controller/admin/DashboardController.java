package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.DashboardResponse;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequest;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardTypeAccountResponse;
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
    private DashBoardService dashBoardService;

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_NUMBER)
    public ResponseEntity<?> dashboardNumber() {
        DashboardResponse dashboardResponse = dashBoardService.dashboardNumber();
        ResponseEntity<DashboardResponse> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_YEARLY_TOTAL_REVENUE)
    public ResponseEntity<?> yearlyTotalRevenue() {
        List<DashboardResponseTotal> dashboardResponse = dashBoardService.yearlyTotalRevenue();
        ResponseEntity<List<DashboardResponseTotal>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_YEARLY_COUNT_SERVICE_REQUEST)
    public ResponseEntity<?> yearlyCountServiceRequest() {
        List<DashboardResponseTotal> dashboardResponse = dashBoardService.yearlyCountServiceRequest();
        ResponseEntity<List<DashboardResponseTotal>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_MONTHLY_ACCOUNT)
    public ResponseEntity<?> monthlyAccount(@RequestParam String year) {
        List<DashboardResponseTotal> dashboardResponse = dashBoardService.monthlyAccount(year);
        ResponseEntity<List<DashboardResponseTotal>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_TYPE_APARTMENT_ACCOUNT)
    public ResponseEntity<?> typeApartmentAccount() {
        List<DashboardTypeAccountResponse> dashboardResponse = dashBoardService.typeApartmentAccount();
        ResponseEntity<List<DashboardTypeAccountResponse>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_MONTHLY_VEHICLE)
    public ResponseEntity<?> monthlyVehicle() {
        List<DashboardResponseTotal> dashboardResponse = dashBoardService.monthlyVehicle();
        ResponseEntity<List<DashboardResponseTotal>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DASHBOARD_DASHBOARD_NUMBER_OF_USER_SERVICE_REQUEST)
    public ResponseEntity<?> numberOfUseServiceRequest() {
        List<DashboardResponseNumberOfUseServiceRequest> dashboardResponse = dashBoardService.numberOfUseServiceRequest();
        ResponseEntity<List<DashboardResponseNumberOfUseServiceRequest>> response = new ResponseEntity<>(dashboardResponse, HttpStatus.CREATED);
        return response;
    }
}
