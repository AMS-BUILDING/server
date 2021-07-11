package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.VehicleCardResponse;
import com.ams.building.server.service.VehicleCardService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vehicle-card/")
@CrossOrigin(origins = "*", maxAge = -1)
public class ParkingCardController {

    private static final Logger logger = Logger.getLogger(ParkingCardController.class);

    @Autowired
    private VehicleCardService vehicleCardService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_VEHICLE_CARD)
    public ResponseEntity<?> searchVehicleCard(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                               @RequestParam(value = "owverName", required = false, defaultValue = "") String owverName,
                                               @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
                                               @RequestParam(value = "lincesPlates", required = false, defaultValue = "") String lincesPlates,
                                               @RequestParam(value = "statusId", required = false, defaultValue = "-1") Long statusId) {

        logger.debug("searchVehicleCard request : " + owverName + " - " + phoneNumber + " - " + lincesPlates + "-" + statusId);
        Integer pageSize = 5;
        ApiResponse apiResponse = vehicleCardService.searchVehicleCard(pageNo, pageSize, owverName, phoneNumber, lincesPlates, statusId);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchVehicleCard response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DETAIL_VEHICLE_CARD + "/{id}")
    public ResponseEntity<?> detailVehicleCard(@PathVariable("id") Long id) {
        logger.debug("detailVehicleCard request : " + id);
        VehicleCardResponse vehicleCardResponse = vehicleCardService.detailVehicleCard(id);
        ResponseEntity<VehicleCardResponse> response = new ResponseEntity<>(vehicleCardResponse, HttpStatus.OK);
        logger.debug("detailVehicleCard response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_UPDATE_VEHICLE_CARD + "/{id}")
    public ResponseEntity<?> updateVehicleCard(@PathVariable("id") Long id, @RequestParam Long statusId) {
        logger.debug("updateVehicleCard request : " + id + "-" + statusId);
        vehicleCardService.updateStatusVehicleCard(id, statusId);
        ResponseEntity<String> response = new ResponseEntity<>("Update success", HttpStatus.OK);
        logger.debug("updateVehicleCard response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_REMOVE_VEHICLE_CARD + "/{id}")
    public ResponseEntity<?> removeVehicleCard(@PathVariable("id") Long id) {
        logger.debug("removeVehicleCard request : " + id);
        vehicleCardService.removeVehicleCard(id);
        ResponseEntity<String> response = new ResponseEntity<>("Remove Success", HttpStatus.OK);
        logger.debug("removeVehicleCard response : " + new Gson().toJson(response));
        return response;
    }

}
