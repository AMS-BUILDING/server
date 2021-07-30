package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.StatusVehicleCardResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.response.VehicleCardResponse;
import com.ams.building.server.response.VehicleTypeResponse;
import com.ams.building.server.service.StatusVehicleCardService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class VehicleCardController {

    private static final Logger logger = Logger.getLogger(VehicleCardController.class);

    @Autowired
    private VehicleCardService vehicleCardService;

    @Autowired
    private StatusVehicleCardService statusVehicleCardService;

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

    @GetMapping(value = Constants.UrlPath.URL_API_LIST_STATUS_VEHICLE_CARD)
    public ResponseEntity<?> listStatusVehicleCard() {
        List<StatusVehicleCardResponse> cardResponses = statusVehicleCardService.listStatus();
        ResponseEntity<List<StatusVehicleCardResponse>> response = new ResponseEntity<>(cardResponses, HttpStatus.OK);
        logger.debug("listStatusVehicleCard response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_VEHICLE_BY_ACCOUNT_ID_AND_VEHICLE_TYPE)
    public ResponseEntity<?> searchVehicleCardByAccountIdAndVehicleType(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(value = "accountId") Long accountId,
                                                                        @RequestParam(value = "vehicleId") Long vehicleId) {
        logger.debug("searchVehicleCardByAccountIdAndVehicleType request : " + accountId + " - " + vehicleId);
        Integer pageSize = 5;
        ApiResponse apiResponse = vehicleCardService.searchVehicleCardByRoomNumber(pageNo, pageSize, accountId, vehicleId);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchVehicleCardByAccountIdAndVehicleType response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_VEHICLE_APP)
    public ResponseEntity<?> listStatusVehicleApp(@RequestParam Long typeId) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<VehicleTypeResponse> vehicleResponse = vehicleCardService.listVehicleByTypeAndByAccountId(currentUser.getId(), typeId);
        ResponseEntity<List<VehicleTypeResponse>> response = new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
        logger.debug("listStatusVehicleApp response : " + new Gson().toJson(response));
        return response;
    }

}
