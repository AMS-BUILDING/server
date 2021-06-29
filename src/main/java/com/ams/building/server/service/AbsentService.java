package com.ams.building.server.service;

import com.ams.building.server.request.AbsentRequest;
import com.ams.building.server.response.ApiResponse;

import javax.servlet.http.HttpServletResponse;

public interface AbsentService {

    ApiResponse absentList(String name, String identifyCard, Long absentType, Integer page, Integer size);

    void exportAbsentDetailList(HttpServletResponse response, String name, String identifyCard, Long absentType, Integer page, Integer size);

    void addAbsentDetail(AbsentRequest request);

}
