package com.ams.building.server.service;

import com.ams.building.server.request.RequestServiceRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.DetailServiceRequestResponse;
import com.ams.building.server.response.DetailSubServiceClientResponse;
import com.ams.building.server.response.HourResponse;
import com.ams.building.server.response.ReasonDetailSubServiceResponse;
import com.ams.building.server.response.RequestServiceClientResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.response.ServiceAddResponse;
import com.ams.building.server.response.StatusServiceResponse;

import java.util.List;

public interface RequestServiceService {

    List<StatusServiceResponse> statusResponses();

    ApiResponse searchServiceRequest(Integer size, Integer pageNo, String name, String serviceName, Long statusId);

    RequestServiceResponse getRequestServiceById(Long id);

    void updateStatusRequest(Long statusId, Long requestId);

    ServiceAddResponse addRequestServiceSuccessStatus(RequestServiceRequest requestServiceRequest);

    Double getPriceByReasonDetailSubServiceId(Long id);

    List<ReasonDetailSubServiceResponse> getListReasonByDetailSubServiceId(Long id);

    List<RequestServiceClientResponse> findRequestServiceByAccountId(Long accountId);

    List<DetailSubServiceClientResponse> getDetailSubServiceBySubServiceId(Long subServiceId);

    List<RequestServiceClientResponse> historyServiceResponse(Long id, Long statusId);

    DetailServiceRequestResponse detailServiceRequest(Long serviceRequestId, Long typeRequest);

    void updateStatusRequestByTypeRequest(Long statusId, Long requestId, Long typeRequest);

    List<HourResponse>listHours();

}
