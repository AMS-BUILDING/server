package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.request.VehicleCardClientRequest;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.response.VehicleTypeResponse;
import com.ams.building.server.service.VehicleCardService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class VehicleCardAppController {

    private static final Logger logger = Logger.getLogger(VehicleCardAppController.class);

    @Autowired
    private VehicleCardService vehicleCardService;

    @GetMapping(value = Constants.UrlPath.URL_API_VEHICLE_APP + "/{id}")
    public ResponseEntity<?> listStatusVehicleApp(@PathVariable("id") Long id, @RequestParam Long typeId) {
        logger.debug("listStatusVehicleApp response : " + id + "-" + typeId);
        List<VehicleTypeResponse> vehicleResponse = vehicleCardService.listVehicleByTypeAndByAccountId(id, typeId);
        ResponseEntity<List<VehicleTypeResponse>> response = new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
        logger.debug("listStatusVehicleApp response : " + new Gson().toJson(response));
        return response;
    }

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
