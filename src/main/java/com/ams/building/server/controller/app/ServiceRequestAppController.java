package com.ams.building.server.controller.app;


import com.google.gson.Gson;
import com.quan_ly_toa_nha.fpt.constant.Constants;
import com.quan_ly_toa_nha.fpt.request.RequestServiceRequest;
import com.quan_ly_toa_nha.fpt.response.ApiResponse;
import com.quan_ly_toa_nha.fpt.response.DetailServiceRequestResponse;
import com.quan_ly_toa_nha.fpt.response.ReasonDetailSubServiceResponse;
import com.quan_ly_toa_nha.fpt.response.UserPrincipal;
import com.quan_ly_toa_nha.fpt.service.ApartmentService;
import com.quan_ly_toa_nha.fpt.service.RequestServiceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ServiceRequestAppController {

    private static final Logger logger = Logger.getLogger(ServiceRequestAppController.class);

    @Autowired
    private RequestServiceService requestServiceService;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping(Constants.UrlPath.URL_API_SEARCH_REASON_DETAIL_SUB_SERVICE)
    public List<ReasonDetailSubServiceResponse> getListReasonByDetailSubServiceId(@RequestParam("detailSubServiceId") Long id) {
        logger.debug("getListReasonByDetailSubServiceId " + new Gson().toJson(id));
        List<ReasonDetailSubServiceResponse> responses = requestServiceService.getListReasonByDetailSubServiceId(id);
        logger.debug("getListReasonByDetailSubServiceId response : " + new Gson().toJson(responses));
        return responses;
    }

    @GetMapping(Constants.UrlPath.URL_API_SERVICE_REQUEST_PRICE)
    public Double getPriceByReasonDetailSubServiceId(@RequestParam("reasonDetailSubServiceId") Long id) {
        logger.debug("getPriceByReasonDetailSubServiceId " + new Gson().toJson(id));
        Double apiResp = requestServiceService.getPriceByReasonDetailSubServiceId(id);
        logger.debug("getPriceByReasonDetailSubServiceId response : " + new Gson().toJson(apiResp));
        return apiResp;
    }

    @PostMapping(Constants.UrlPath.URL_API_ADD_SERVICE_REQUEST)
    public ResponseEntity<?> addRequestServiceSuccessStatus(@RequestBody RequestServiceRequest requestServiceRequest) {
        logger.debug("addRequestServiceSuccessStatus " + new Gson().toJson(requestServiceRequest));
        Long id = requestServiceService.addRequestServiceSuccessStatus(requestServiceRequest);
        ResponseEntity<Long> response = new ResponseEntity(id, HttpStatus.CREATED);
        logger.debug("Add RequestService response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_TYPE_APARTMENT)
    public ResponseEntity<?> typeApartmentByAccountId() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        String typeApartment = apartmentService.typeApartmentByAccountId(accountId);
        ResponseEntity<String> response = new ResponseEntity(typeApartment, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_LIST_SERVICE_REQUEST)
    public ResponseEntity<?> findRequestServiceByAccountId(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        logger.debug("findRequestServiceByAccountId request : " + accountId);
        Integer pageSize = 5;
        ApiResponse apiResponse = requestServiceService.findRequestServiceByAccountId(accountId, pageSize, pageNo);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("findRequestServiceByAccountId response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_STATUS_SERVICE_REQUEST)
    public ResponseEntity<?> statusServiceRequest(@RequestParam Long serviceRequestId) {
        logger.debug("statusServiceRequest request: " + serviceRequestId);
        DetailServiceRequestResponse requestResponse = requestServiceService.detailServiceRequest(serviceRequestId);
        ResponseEntity<DetailServiceRequestResponse> response = new ResponseEntity<>(requestResponse, HttpStatus.OK);
        logger.debug("statusServiceRequest response: " + new Gson().toJson(response));
        return response;
    }
}
