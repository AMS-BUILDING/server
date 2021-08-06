package com.ams.building.server.controller.app;

import com.google.gson.Gson;
import com.quan_ly_toa_nha.fpt.constant.Constants;
import com.quan_ly_toa_nha.fpt.request.VehicleCardClientRequest;
import com.quan_ly_toa_nha.fpt.response.UserPrincipal;
import com.quan_ly_toa_nha.fpt.service.VehicleCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class VehicleCardAppController {

    private static final Logger logger = Logger.getLogger(VehicleCardAppController.class);

    @Autowired
    private VehicleCardService vehicleCardService;

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_VEHICLE_CARD_CLIENT)
    public ResponseEntity<?> addVehicleCard(@RequestBody List<VehicleCardClientRequest> requests) {
        logger.debug("addVehicleCard " + new Gson().toJson(requests));
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long accountId = currentUser.getId();
        vehicleCardService.addVehicleCard(requests, accountId);
        ResponseEntity<String> response = new ResponseEntity<>("Add Vehicle Card  success", HttpStatus.CREATED);
        logger.debug("addVehicleCard response : " + new Gson().toJson(response));
        return response;
    }

}
