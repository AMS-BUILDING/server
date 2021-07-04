package com.ams.building.server.service;

import com.ams.building.server.request.EmployeeRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.EmployeeResponse;

public interface EmployeeService {

    ApiResponse searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Integer page, Integer size, String name, String phoneNumber, String identifyCard, Long position, String roles);

    void removeEmployee(Long id,String role);

    void updateEmployee(Long accountId, EmployeeRequest request);

    void addEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long accountId, String role);

}
