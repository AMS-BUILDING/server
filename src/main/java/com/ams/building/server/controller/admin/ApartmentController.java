package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.PropertyKeys;
import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.request.ResidentRequestWrap;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.RoomNumberResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.service.ApartmentService;
import com.ams.building.server.service.EmailService;
import com.ams.building.server.utils.PropertiesReader;
import com.google.gson.Gson;
import com.quan_ly_toa_nha.fpt.constant.Constants;
import com.quan_ly_toa_nha.fpt.constant.PropertyKeys;
import com.quan_ly_toa_nha.fpt.request.AResidentRequest;
import com.quan_ly_toa_nha.fpt.request.ApartmentOwnerRequest;
import com.quan_ly_toa_nha.fpt.request.ResidentRequest;
import com.quan_ly_toa_nha.fpt.request.ResidentRequestWrap;
import com.quan_ly_toa_nha.fpt.request.UpdateResidentRequest;
import com.quan_ly_toa_nha.fpt.response.AccountResponse;
import com.quan_ly_toa_nha.fpt.response.ApiResponse;
import com.quan_ly_toa_nha.fpt.response.RoomNumberResponse;
import com.quan_ly_toa_nha.fpt.response.UserPrincipal;
import com.quan_ly_toa_nha.fpt.service.AccountService;
import com.quan_ly_toa_nha.fpt.service.ApartmentService;
import com.quan_ly_toa_nha.fpt.service.EmailService;
import com.quan_ly_toa_nha.fpt.utils.PropertiesReader;
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

    @PostMapping(Constants.UrlPath.URL_API_VALIDATE_OWNER)
    public ResponseEntity<?> validateOwnerRequest(@RequestBody ApartmentOwnerRequest ownerRequest) {
        logger.debug("validateOwnerRequest Request : " + new Gson().toJson(ownerRequest));
        accountService.validateApartmentOwner(ownerRequest);
        ResponseEntity<String> response = new ResponseEntity<>("Validate success", HttpStatus.OK);
        logger.debug("validateOwnerRequest response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_VALIDATE_RESIDENT)
    public ResponseEntity<?> validateListResident(@RequestBody ResidentRequestWrap requestWrap) {
        logger.debug("validateListResident Request : " + new Gson().toJson(requestWrap));
        accountService.validateListResident(requestWrap.getResidentRequestList(), requestWrap.getOwnerRequest());
        ResponseEntity<String> response = new ResponseEntity<>("Validate success", HttpStatus.OK);
        logger.debug("validateListResident response : " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_APARTMENT_OWNER)
    public ResponseEntity<?> addOwner(@RequestBody ApartmentOwnerRequest ownerRequest) throws MessagingException {
        logger.debug("addOwner Request : " + new Gson().toJson(ownerRequest));
        Long accountId = accountService.addApartmentOwner(ownerRequest);
        apartmentService.addOwnerToApartment(ownerRequest.getApartmentId(), accountId);
        ResponseEntity<String> response = new ResponseEntity<>("Add apartment owner success", HttpStatus.CREATED);
        //send email
        String emailText = "Hello and welcome new resident! \n" + "Here is the password for your first login \n" + " User name : " + ownerRequest.getName() + "\n" + " Email: " + ownerRequest.getEmail() + "\n" + " Password: " + Constants.DEFAULT_PASSWORD_ENCODE + "\n" + "We hope you have a nice day!”";
        emailService.sendSimpleMessage(ownerRequest.getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), emailText);
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
                e.printStackTrace();
            }
        });
        ResponseEntity<String> response = new ResponseEntity<>("Add Resident success", HttpStatus.CREATED);
        logger.debug("Add Resident response : " + new Gson().toJson(response));
        return response;
    }

    private void sendResidentEmail(ResidentRequest residentRequest) throws MessagingException {
        String emailText = "Hello and welcome new resident! \n" + "Here is the password for your first login \n" + " User name : " + residentRequest.getName() + "\n" + " Email: " + residentRequest.getEmail() + "\n" + " Password: " + Constants.DEFAULT_PASSWORD_ENCODE + "\n" + "We hope you have a nice day!”";
        emailService.sendSimpleMessage(residentRequest.getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), emailText);
    }

//    @PostMapping(value = Constants.UrlPath.URL_API_DISABLE_APARTMENT)
//    public ResponseEntity<?> disableOwner(@RequestParam(name = "roomNumberId") Long roomNumberId) {
//        logger.debug("disableOwner Request : " + new Gson().toJson(roomNumberId));
//        apartmentService.disableApartment(roomNumberId);
//        ResponseEntity<String> response = new ResponseEntity<>("disableOwner success", HttpStatus.CREATED);
//        return response;
//    }

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

    @PostMapping(value = Constants.UrlPath.URL_API_UPDATE_RESIDENT)
    public ResponseEntity<?> residentUpdate(@RequestBody UpdateResidentRequest residentRequest) {
        accountService.updateResident(residentRequest);
        ResponseEntity<String> response = new ResponseEntity<>("Update resident success", HttpStatus.OK);
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_ROOM_NUMBER_SEARCH)
    public ResponseEntity<?> searchRoomNumber(@RequestParam(name = "floorId") Long floorId,
                                              @RequestParam(name = "blockId") Long blockId) {
        logger.debug("searchRoomNumber request : " + floorId + " - " + blockId);
        List<RoomNumberResponse> apiResponse = apartmentService.roomNumberList(blockId, floorId);
        ResponseEntity<List<RoomNumberResponse>> responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("RoomNumberList response" + new Gson().toJson(responseEntity));
        return responseEntity;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_DEPENDENT_PERSON_APP)
    public ResponseEntity<?> dependentPersonByRoomNumber() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = currentUser.getId();
        logger.debug("dependentPersonByRoomNumber request : " + id);
        List<AccountResponse> dependentPerson = apartmentService.dependentPerson(id);
        ResponseEntity<List<AccountResponse>> response = new ResponseEntity<>(dependentPerson, HttpStatus.OK);
        logger.debug("dependentPersonByRoomNumber response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = Constants.UrlPath.URL_API_ADD_A_APARTMENT)
    public ResponseEntity<?> addAResident(@RequestBody AResidentRequest request) throws MessagingException {
        logger.debug("addOwner Request : " + new Gson().toJson(request));
        apartmentService.addResidentToApartment(request.getApartmentId(), request.getRequest());
        ResponseEntity<String> response = new ResponseEntity<>("Add apartment success", HttpStatus.CREATED);
        //send email
        if (!request.getRequest().getEmail().isEmpty()) {
            String emailText = "Hello and welcome new resident! \n" + "Here is the password for your first login \n" +
                    " User name : " + request.getRequest().getName() + "\n" + " Email: " + request.getRequest().getEmail()
                    + "\n" + " Password: " + Constants.DEFAULT_PASSWORD_ENCODE + "\n" + "We hope you have a nice day!”";
            emailService.sendSimpleMessage(request.getRequest().getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), emailText);
        }
        logger.debug("Add apartmentOwner response : " + new Gson().toJson(response));
        return response;
    }

}