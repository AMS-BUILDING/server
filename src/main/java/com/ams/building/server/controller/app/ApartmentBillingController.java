package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.NotificationFeeApartmentResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.ApartmentBillingService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ApartmentBillingController {

    private static final Logger logger = Logger.getLogger(ApartmentBillingController.class);

    @Autowired
    private ApartmentBillingService apartmentBillingService;

    @GetMapping(value = Constants.UrlPath.URL_API_NOTIFICATION_FEE_APARTMENT)
    public ResponseEntity<?> notificationApartment(@RequestParam("billingMonth") String billingMonth) {
        logger.debug("notificationApartment request : " + billingMonth);
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        NotificationFeeApartmentResponse listResponse = apartmentBillingService.getListFeeBillingByMonthAndAccount(accountId, billingMonth);
        ResponseEntity<NotificationFeeApartmentResponse> response = new ResponseEntity(listResponse, HttpStatus.OK);
        logger.debug("notificationApartment response : " + new Gson().toJson(response));
        return response;
    }

}
