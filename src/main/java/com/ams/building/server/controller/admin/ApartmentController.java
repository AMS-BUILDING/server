package com.ams.building.server.controller.admin;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.PropertyKeys;
import com.ams.building.server.request.AResidentRequest;
import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.request.ResidentRequestWrap;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.service.ApartmentService;
import com.ams.building.server.service.EmailService;
import com.ams.building.server.utils.PropertiesReader;
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

    @GetMapping(value = Constants.UrlPath.URL_API_EXPORT_APARTMENT)
    public void exportApartment(HttpServletResponse httpServletResponse,
                                @RequestParam(name = "householderName", required = false, defaultValue = "") String householderName,
                                @RequestParam(name = "roomName", required = false, defaultValue = "") String roomName) {
        logger.debug("exportApartment request : " + householderName + " - " + roomName);
        apartmentService.exportApartmentList(httpServletResponse, roomName, householderName);
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
        StringBuilder content = new StringBuilder();
        content.append("\"Xin chào cư dân");
        content.append(" <p>Dưới đây là tài khoản và mật khẩu để bạn đăng nhập vào app </p>");
        content.append("<p>  Tên đăng nhập :  \"" + ownerRequest.getEmail() + "\"   </p>");
        content.append("<p>  Mật khẩu :  \"" + Constants.DEFAULT_PASSWORD_ENCODE + "\"   </p>");
        content.append("<p> Chúng tôi hi vọng bạn có một ngày tuyệt vời! </p>");
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
        content.append("\"Xin chào quý cư dân!");
        content.append(" <p> Quý cư dân đã đăng ký tài khoản để sử dụng ứng dụng trên điện thoại. </p>");
        content.append(" <p> Dưới đây là tài khoản và mật khẩu của bạn để đăng nhập vào ứng dụng </p>");
        content.append("<p>  Tên đăng nhập :  \"" + residentRequest.getEmail() + "\"   </p>");
        content.append("<p>  Mật khẩu :  \"" + Constants.DEFAULT_PASSWORD_ENCODE + "\"   </p>");
        content.append("<p> Chúng tôi chúc quý cư dân có một ngày tốt lành! </p>");
        emailService.sendSimpleMessage(residentRequest.getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), content.toString());
    }

    @PostMapping(value = Constants.UrlPath.URL_API_DISABLE_APARTMENT + "/{id}")
    public ResponseEntity<?> removeResident(@PathVariable("id") Long id, @RequestParam Long apartmentId) {
        accountService.delete(id, apartmentId);
        ResponseEntity<String> response = new ResponseEntity<>("remove success", HttpStatus.CREATED);
        return response;
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
            StringBuilder content = new StringBuilder();
            content.append("\"Xin chào quý cư dân!");
            content.append(" <p> Quý cư dân đã đăng ký tài khoản để sử dụng ứng dụng trên điện thoại. </p>");
            content.append(" <p> Dưới đây là tài khoản và mật khẩu của bạn để đăng nhập vào ứng dụng </p>");
            content.append("<p>  Tên đăng nhập :  \"" + request.getRequest().getEmail() + "\"   </p>");
            content.append("<p>  Mật khẩu :  \"" + Constants.DEFAULT_PASSWORD_ENCODE + "\"   </p>");
            content.append("<p> Chúng tôi chúc quý cư dân có một ngày tốt lành! </p>");
            emailService.sendSimpleMessage(request.getRequest().getEmail(), PropertiesReader.getProperty(PropertyKeys.SEND_EMAIL_ADD_APARTMENT), content.toString());
        }
        logger.debug("Add apartmentOwner response : " + new Gson().toJson(response));
        return response;
    }

}
