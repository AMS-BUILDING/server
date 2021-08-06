package com.ams.building.server.controller.app;

import com.google.gson.Gson;
import com.quan_ly_toa_nha.fpt.constant.Constants;
import com.quan_ly_toa_nha.fpt.response.UserPrincipal;
import com.quan_ly_toa_nha.fpt.service.ResidentCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ResidentCardAppController {

    private static final Logger logger = Logger.getLogger(VehicleCardAppController.class);

    @Autowired
    private ResidentCardService residentCardService;

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_RESIDENT_CARD_CLIENT)
    public ResponseEntity<?> addResidentCard(@RequestParam Long amount) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        residentCardService.addResidentCard(amount, accountId);
        ResponseEntity<String> response = new ResponseEntity<>("Add Resident Card success", HttpStatus.CREATED);
        logger.debug("addResidentCard response : " + new Gson().toJson(response));
        return response;
    }

}
