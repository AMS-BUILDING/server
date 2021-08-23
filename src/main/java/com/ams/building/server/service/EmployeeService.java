package com.ams.building.server.service;

import com.ams.building.server.request.EmployeeRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.EmployeeResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface EmployeeService {

    ApiResponse searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Integer page, Integer size, String name, String phoneNumber, String identifyCard, Long position, List<String> roles);

    void removeEmployee(Long id);

    void updateEmployee(Long accountId, EmployeeRequest request);

    void addEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long accountId);

    void downloadSearchEmployee(HttpServletResponse httpServletResponse, String name, String phoneNumber, String identifyCard, Long position, List<String> roles);

}
