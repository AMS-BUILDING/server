package com.ams.building.server.controller;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.request.EmployeeRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.EmployeeResponse;
import com.ams.building.server.service.EmployeeService;
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

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class EmployeeController {

    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = Constants.UrlPath.URL_API_SEARCH_EMPLOYEE)
    public ResponseEntity<?> searchEmployee(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                            @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                            @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
                                            @RequestParam(value = "identifyCard", required = false, defaultValue = "") String identifyCard,
                                            @RequestParam(value = "positionId", required = false, defaultValue = "-1") Long positionId) {
        logger.debug("searchEmployee request : " + name + " - " + identifyCard + " - " + phoneNumber + " - " + positionId);
        Integer pageSize = 5;
        String roles = String.valueOf(RoleEnum.ROLE_EMPLOYEE);
        ApiResponse apiResponse = employeeService.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(pageNo, pageSize, name, phoneNumber, identifyCard, positionId, roles);
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        logger.debug("searchEmployee response : " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(Constants.UrlPath.URL_API_GET_ONE_EMPLOYEE + "/{id}")
    public ResponseEntity<?> getOneEmployeeById(@PathVariable("id") Long accountId) {
        logger.debug("getOneEmployeeById: request " + accountId);
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(accountId);
        ResponseEntity<EmployeeResponse> response = new ResponseEntity<>(employeeResponse, HttpStatus.OK);
        logger.debug("getOneEmployeeById response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_REMOVE_EMPLOYEE + "/{id}")
    public ResponseEntity<?> removeEmployee(@PathVariable("id") Long accountId) {
        logger.debug("removeEmployee: request " + accountId);
        employeeService.removeEmployee(accountId);
        ResponseEntity<String> response = new ResponseEntity<>("Remove success", HttpStatus.OK);
        logger.debug("removeEmployee response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_UPDATE_EMPLOYEE + "/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long accountId, @RequestBody EmployeeRequest request) {
        logger.debug("updateEmployee: request " + new Gson().toJson(request));
        employeeService.updateEmployee(accountId, request);
        ResponseEntity<String> response = new ResponseEntity<>("Update Success", HttpStatus.OK);
        logger.debug("updateEmployee response: " + new Gson().toJson(response));
        return response;
    }

    @PostMapping(Constants.UrlPath.URL_API_ADD_EMPLOYEE)
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest request) {
        logger.debug("addEmployee: request " + new Gson().toJson(request));
        employeeService.addEmployee(request);
        ResponseEntity<String> response = new ResponseEntity<>("Add Success", HttpStatus.CREATED);
        logger.debug("addEmployee response: " + new Gson().toJson(response));
        return response;
    }

    @GetMapping(value = Constants.UrlPath.URL_API_EXPORT_SEARCH_EMPLOYEE)
    public void exportEmployeeSearch(HttpServletResponse httpServletResponse,
                                     @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
                                     @RequestParam(value = "identifyCard", required = false, defaultValue = "") String identifyCard,
                                     @RequestParam(value = "positionId", required = false, defaultValue = "-1") Long positionId) {
        String roles = String.valueOf(RoleEnum.ROLE_EMPLOYEE);
        employeeService.downloadSearchEmployee(httpServletResponse, name, phoneNumber, identifyCard, positionId, roles);
    }

}
