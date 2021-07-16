package com.ams.building.server.service.impl;

import com.ams.building.server.bean.ReasonDetailSubService;
import com.ams.building.server.bean.ServiceBean;
import com.ams.building.server.bean.SubService;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.ReasonDetailSubServiceDAO;
import com.ams.building.server.dao.ServiceDAO;
import com.ams.building.server.dao.SubServiceDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.DetailSubServiceResponse;
import com.ams.building.server.response.ServiceResponse;
import com.ams.building.server.response.SubServiceResponse;
import com.ams.building.server.service.SubServiceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.HelperUtils.formatDoubleNUmber;

@Service
public class SubServiceServiceImpl implements SubServiceService {

    private static final Logger logger = Logger.getLogger(SubServiceServiceImpl.class);

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private SubServiceDAO subServiceDAO;

    @Autowired
    private ReasonDetailSubServiceDAO reasonDetailSubServiceDAO;

    @Override
    public List<ServiceResponse> serviceList() {
        List<ServiceBean> serviceBeans = serviceDAO.serviceList();
        List<ServiceResponse> responses = new ArrayList<>();
        serviceBeans.forEach(s -> responses.add(covertServiceResponse(s)));
        return responses;
    }

    @Override
    public ApiResponse searchService(Integer page, Integer size, Long serviceId, String subServiceName) {
        List<SubServiceResponse> subServices = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<SubService> subServicesPage;
        if (serviceId == -1) {
            subServicesPage = subServiceDAO.searchSubServiceBySubServiceName(subServiceName, pageable);
        } else {
            subServicesPage = subServiceDAO.searchSubServiceBySubServiceNameAndServiceId(subServiceName, serviceId, pageable);
        }
        for (SubService ad : subServicesPage) {
            SubServiceResponse response = covertSubServiceResponse(ad);
            subServices.add(response);
        }
        Long totalElement = subServicesPage.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(subServices).totalElement(totalElement).build();
        return response;
    }

    @Override
    public List<DetailSubServiceResponse> reasonDetailServiceBySubServiceId(Long subServiceId) {
        if (StringUtils.isEmpty(subServiceId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        SubService subService = subServiceDAO.getOne(subServiceId);
        if (Objects.isNull(subService)) {
            throw new RestApiException(StatusCode.SUB_SERVICE_NOT_EXIST);
        }
        List<ReasonDetailSubService> detailSubServices = reasonDetailSubServiceDAO.getListReasonBySubServiceId(subServiceId);
        if (detailSubServices.isEmpty()) {
            throw new RestApiException(StatusCode.DETAIL_SUB_SERVICE_NOT_EXIST);
        }
        List<DetailSubServiceResponse> listResponse = new ArrayList<>();
        detailSubServices.forEach(s -> listResponse.add(covertDetailServiceResponse(s)));
        return listResponse;
    }

    private ServiceResponse covertServiceResponse(ServiceBean service) {
        ServiceResponse response = ServiceResponse.builder().id(service.getId()).serviceName(service.getName()).build();
        return response;
    }

    private SubServiceResponse covertSubServiceResponse(SubService service) {
        SubServiceResponse response = SubServiceResponse.builder()
                .subSerivceId(service.getId())
                .serviceName(service.getService().getName())
                .subServiceName(service.getSubServiceName()).build();
        return response;
    }

    private DetailSubServiceResponse covertDetailServiceResponse(ReasonDetailSubService service) {
        DetailSubServiceResponse response = DetailSubServiceResponse.builder().build();
        response.setServiceName(service.getDetailSubService().getService().getService().getName());
        response.setSubServiceName(service.getDetailSubService().getService().getSubServiceName());
        response.setDetailSubServiceName(service.getDetailSubService().getDetailSubServiceName());
        response.setReasonName(service.getReasonName());
        if (!StringUtils.isEmpty(service.getPrice())) {
            response.setPrice(formatDoubleNUmber(service.getPrice()));
        }
        return response;
    }
}
