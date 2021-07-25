package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.RequestService;
import com.ams.building.server.bean.StatusServiceRequest;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.dao.StatusRequestServiceDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.HistoryRequestServiceResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.response.StatusServiceResponse;
import com.ams.building.server.service.RequestServiceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.HH_MM;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;

@Transactional
@Service
public class RequestServiceServiceImpl implements RequestServiceService {

    private static final Logger logger = Logger.getLogger(RequestServiceServiceImpl.class);

    @Autowired
    private StatusRequestServiceDAO statusRequestServiceDAO;

    @Autowired
    private RequestServiceDAO requestServiceDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public List<StatusServiceResponse> statusResponses() {
        List<StatusServiceResponse> responses = new ArrayList<>();
        List<StatusServiceRequest> requests = statusRequestServiceDAO.requestStatusList();
        requests.forEach(s -> responses.add(covertToStatusRequestResponse(s)));
        return responses;
    }

    @Override
    public ApiResponse searchServiceRequest(Integer size, Integer page, String name, String serviceName, Long statusId) {
        List<RequestServiceResponse> requestServiceResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestService> requestServices;
        if (statusId == -1) {
            requestServices = requestServiceDAO.requestServicesNotStatus(name, serviceName, pageable);
        } else {
            requestServices = requestServiceDAO.requestServicesWithStatus(name, serviceName, statusId, pageable);
        }
        requestServices.forEach(s -> requestServiceResponses.add(covertToRequestResponse(s)));
        Long totalElement = requestServices.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(requestServiceResponses).totalElement(totalElement).build();
        return response;
    }

    @Override
    public RequestServiceResponse getRequestServiceById(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        RequestService service = requestServiceDAO.findRequestServiceById(id);
        if (Objects.isNull(service)) {
            throw new RestApiException(StatusCode.REQUEST_SERVICE_NOT_EXIST);
        }
        RequestServiceResponse response = covertToRequestResponse(service);
        return response;
    }

    @Override
    public void updateStatusRequest(Long statusId, Long requestId) {
        if (StringUtils.isEmpty(requestId) || StringUtils.isEmpty(statusId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        RequestService service = requestServiceDAO.findRequestServiceById(requestId);
        if (Objects.isNull(service)) {
            throw new RestApiException(StatusCode.REQUEST_SERVICE_NOT_EXIST);
        }
        requestServiceDAO.updateStatus(statusId, requestId);
    }

    @Override
    public List<HistoryRequestServiceResponse> historyServiceResponse(Long id, Long statusId) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(statusId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        List<RequestService> requestServiceByAccountId = requestServiceDAO.requestServiceByAccountId(id, statusId);
        List<HistoryRequestServiceResponse> responses = new ArrayList<>();
        requestServiceByAccountId.forEach(s -> responses.add(convertToHistoryResponse(s)));
        return responses;
    }

    private StatusServiceResponse covertToStatusRequestResponse(StatusServiceRequest request) {
        StatusServiceResponse response = StatusServiceResponse.builder().build();
        response.setId(request.getId());
        response.setName(request.getRequestName());
        return response;
    }

    private RequestServiceResponse covertToRequestResponse(RequestService requestService) {
        RequestServiceResponse response = RequestServiceResponse.builder().build();
        if (Objects.isNull(requestService.getAccount())) throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        Apartment apartment = apartmentDAO.getApartmentByAccountId(requestService.getAccount().getId(), String.valueOf(RoleEnum.ROLE_LANDLORD));
        if (Objects.isNull(apartment)) throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        response.setId(requestService.getId());
        response.setBlock(apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName());
        response.setServiceName(requestService.getReasonDetailSubService().getReasonName());
        response.setStatus(requestService.getStatusServiceRequest().getRequestName());
        response.setRoomName(apartment.getRoomNumber().getRoomName());
        response.setName(requestService.getAccount().getName());
        return response;
    }

    private HistoryRequestServiceResponse convertToHistoryResponse(RequestService requestService) {
        HistoryRequestServiceResponse response = HistoryRequestServiceResponse.builder().build();
        String serviceName = requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
        String firstDescription = requestService.getStatusServiceRequest().getId() == 3 ? "Đã đăng kí thành công dịch vụ " : "Yêu cầu dịch vụ ";
        String serviceNameFirst = requestService.getStatusServiceRequest().getId() == 3 ? "Sử dụng dịch vụ " : "Yêu cầu dịch vụ ";
        String date = convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null);
        String time = convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        response.setId(requestService.getId());
        response.setServiceName(serviceNameFirst + serviceName + " - " + date);
        response.setDescription(firstDescription + serviceName + " vào lúc " + time + " ngày " + date);
        return response;
    }

}
