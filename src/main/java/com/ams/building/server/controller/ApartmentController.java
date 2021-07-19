package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.PropertyKeys;
import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.request.ResidentRequestWrap;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.AccountDetailResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.service.ApartmentService;
import com.ams.building.server.service.EmailService;
import com.ams.building.server.utils.PropertiesReader;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ApartmentController {

    private static final Logger logger = Logger.getLogger(ApartmentController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private EmailService emailService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_APARTMENT)
    public ResponseEntity<?> searchApartment(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                             @RequestParam(name = "householderName", required = false, defaultValue = "") String householderName,
                                             @RequestParam(name = "roomName", required = false, defaultValue = "") String roomName) {
        logger.debug("searchApartment request : " + householderName + " - " + roomName);
        Integer pageSize = 5;
        ApiResponse apiResponse = apartmentService.apartmentList(householderName, roomName, pageNo, pageSize);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchApartment response : " + new Gson().toJson(response));
        return response;

    }

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_APARTMENT_OWNER)
    public ResponseEntity<?> addOwner(@RequestBody ApartmentOwnerRequest ownerRequest) throws MessagingException {
        logger.debug("addOwner Request : " + new Gson().toJson(ownerRequest));
        Long accountId = accountService.addApartmentOwner(ownerRequest);
        apartmentService.addOwnerToApartment(ownerRequest.getApartmentId(), accountId);
        ResponseEntity<String> response = new ResponseEntity<>("Add apartment owner success", HttpStatus.CREATED);
        //send email
        StringBuilder content = new StringBuilder();
        content.append("\"Hello and welcome new resident!");
        content.append(" <p>Here is the password for your first login </p>");
        content.append("<p>  Username :  \"" + ownerRequest.getEmail() + "\"   </p>");
        content.append("<p>  Password :  \"" + Constants.DEFAULT_PASSWORD_ENCODE + "\"   </p>");
        content.append("<p> We hope you have a nice day! </p>");
        emailService.sendSimpleMessage(ownerRequest.getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), content.toString());
        logger.debug("Add apartmentOwner response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_APARTMENT_RESIDENT)
    public ResponseEntity<?> addResident(@RequestBody ResidentRequestWrap requestWrap) {
        logger.debug("addResident Request : " + new Gson().toJson(requestWrap));
        List<Long> residentId = accountService.addListResident(requestWrap.getResidentRequestList());
        apartmentService.addListResidentToApartment(requestWrap.getApartmentId(), residentId);
        //send email
        requestWrap.getResidentRequestList().forEach(residentRequest -> {
            try {
                sendResidentEmail(residentRequest);
            } catch (MessagingException e) {
                logger.error("addResident" + e);
            }
        });

        ResponseEntity<String> response = new ResponseEntity<>("Add Resident success", HttpStatus.CREATED);
        logger.debug("Add Resident response : " + new Gson().toJson(response));
        return response;
    }

    private void sendResidentEmail(ResidentRequest residentRequest) throws MessagingException {
        StringBuilder content = new StringBuilder();
        content.append("\"Hello and welcome new resident!");
        content.append(" <p>Here is the password for your first login </p>");
        content.append("<p>  Username :  \"" + residentRequest.getEmail() + "\"   </p>");
        content.append("<p>  Password :  \"" + Constants.DEFAULT_PASSWORD_ENCODE + "\"   </p>");
        content.append("<p> We hope you have a nice day! </p>");
        emailService.sendSimpleMessage(residentRequest.getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), content.toString());
    }

    @PostMapping(value = Constants.UrlPath.URL_API_DISABLE_APARTMENT)
    public ResponseEntity<?> disableOwner(@RequestParam(name = "roomNumberId") Long roomNumberId) {
        logger.debug("disableOwner Request : " + new Gson().toJson(roomNumberId));
        apartmentService.disableApartment(roomNumberId);
        ResponseEntity<String> response = new ResponseEntity<>("disableOwner success", HttpStatus.CREATED);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_EXPORT_APARTMENT)
    public void exportApartment(HttpServletResponse httpServletResponse,
                                @RequestParam(name = "householderName", required = false, defaultValue = "") String householderName,
                                @RequestParam(name = "roomName", required = false, defaultValue = "") String roomName) {
        logger.debug("exportApartment request : " + householderName + " - " + roomName);
        apartmentService.exportApartmentList(httpServletResponse, roomName, householderName);
    }

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_APARTMENT_RESIDENT)
    public ResponseEntity<?> residentOfApartment(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                 @RequestParam(name = "name", required = false, defaultValue = "") String name,
                                                 @RequestParam(name = "roomNumber", required = false, defaultValue = "") String roomNumber,
                                                 @RequestParam(name = "phone", required = false, defaultValue = "") String phone) {
        logger.debug("residentOfApartment request : " + name + " - " + roomNumber + " - " + phone);
        Integer pageSize = 5;
        ApiResponse apiResponse = apartmentService.accountOfApartment(name, roomNumber, phone, pageNo, pageSize);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("residentOfApartment response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_ACCOUNT_DETAIL)
    public ResponseEntity<?> accountDetail(@RequestParam(name = "apartmentId") Long apartmentId,
                                           @RequestParam(name = "accountId") Long accountId) {
        logger.debug("accountDetail request : " + apartmentId + " - " + accountId);
        AccountDetailResponse accountDetailResponse = apartmentService.getAccountDetail(accountId, apartmentId);
        ResponseEntity<AccountDetailResponse> response = new ResponseEntity<>(accountDetailResponse, HttpStatus.OK);
        logger.debug("accountDetail response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_UPDATE_RESIDENT)
    public ResponseEntity<?> residentUpdate(@RequestBody UpdateResidentRequest residentRequest) {
        accountService.updateResident(residentRequest);
        ResponseEntity<String> response = new ResponseEntity<>("Update resident success", HttpStatus.OK);
        return response;
    }

}
