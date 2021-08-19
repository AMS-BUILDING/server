package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.BillingApartmentService;
import com.ams.building.server.service.BillingDetailApartmentService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class BillingApartmentController {

    private static final Logger logger = Logger.getLogger(BillingApartmentController.class);

    @Autowired
    private BillingApartmentService billingApartmentService;

    @Autowired
    private BillingDetailApartmentService billingDetailApartmentService;

    @GetMapping(value = Constants.UrlPath.URL_API_FEE_SERVICE)
    public ResponseEntity<?> searchServiceByMonth(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                  @RequestParam(value = "month") String month) {
        logger.debug("searchServiceByMonth request : " + month);
        Integer pageSize = 5;
        ApiResponse apiResponse = billingDetailApartmentService.searchBillingDetailAboutServiceByMonth(pageNo, pageSize, month);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchServiceByMonth response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_FEE_TOTAL_CARD)
    public ResponseEntity<?> searchTotalCardByMonth(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(value = "month") String month) {
        logger.debug("searchTotalCardByMonth request : " + month);
        Integer pageSize = 5;
        ApiResponse apiResponse = billingDetailApartmentService.searchBillingDetailAboutCardByMonth(pageNo, pageSize, month);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchTotalCardByMonth response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_FEE_APARTMENT_TOTAL)
    public ResponseEntity<?> searchTotalByMonth(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                @RequestParam(value = "month") String month) {
        logger.debug("searchTotalByMonth request : " + month);
        Integer pageSize = 5;
        ApiResponse apiResponse = billingApartmentService.searchBuildingApartmentByMonth(pageNo, pageSize, month);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchTotalByMonth response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_FEE_APARTMENT_SEARCH_RESIDENT_CARD)
    public ResponseEntity<?> searchBillingDetailAboutResidentCardByAccountIdAndMonth(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                                                     @RequestParam(value = "accountId") Long accountId,
                                                                                     @RequestParam(value = "month") String month) {
        logger.debug("searchBillingDetailAboutResidentCardByAccountIdAndMonth request : " + month + "-" + accountId);
        Integer pageSize = 5;
        ApiResponse apiResponse = billingDetailApartmentService.searchBillingDetailAboutResidentCardByAccountIdAndMonth(pageNo, pageSize, accountId, month);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchBillingDetailAboutResidentCardByAccountIdAndMonth response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_FEE_APARTMENT_SEARCH_VEHICLE_CARD)
    public ResponseEntity<?> searchBillingDetailAboutVehicleCardByAccountIdAndMonth(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                                                    @RequestParam(value = "accountId") Long accountId,
                                                                                    @RequestParam(value = "month") String month) {
        logger.debug("searchBillingDetailAboutVehicleCardByAccountIdAndMonth request : " + month + "-" + accountId);
        Integer pageSize = 5;
        ApiResponse apiResponse = billingDetailApartmentService.searchBillingDetailAboutVehicleCardByAccountIdAndMonth(pageNo, pageSize, accountId, month);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchBillingDetailAboutVehicleCardByAccountIdAndMonth response : " + new Gson().toJson(response));
        return response;
    }
}
