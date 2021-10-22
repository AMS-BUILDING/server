package com.ams.building.server.service;

import com.ams.building.server.bean.DetailSubService;
import com.ams.building.server.bean.ReasonDetailSubService;
import com.ams.building.server.bean.ServiceBean;
import com.ams.building.server.bean.SubService;
import com.ams.building.server.dao.ReasonDetailSubServiceDAO;
import com.ams.building.server.dao.ServiceDAO;
import com.ams.building.server.dao.SubServiceDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.DetailSubServiceResponse;
import com.ams.building.server.response.ServiceResponse;
import com.ams.building.server.response.SubServiceResponse;
import com.ams.building.server.service.impl.SubServiceServiceImpl;
import com.ams.building.server.utils.HelperUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class SubServiceServiceImplTest {

    @Mock
    private ServiceDAO serviceDAO;

    @Mock
    private SubServiceDAO subServiceDAO;

    @Mock
    private ReasonDetailSubServiceDAO reasonDetailSubServiceDAO;

    @InjectMocks
    private SubServiceServiceImpl subServiceServiceImpl;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    Pageable pageable = PageRequest.of(1, 5);
    ServiceBean serviceBean = new ServiceBean(1L, "service");
    SubService subService = new SubService(1L, serviceBean, "BUPFEE");
    DetailSubService detailSubService = new DetailSubService(1L, subService, "detail");
    ReasonDetailSubService reasonDetailSubService = new ReasonDetailSubService(1L, detailSubService,
            "reasonName", 1000D);
    List<ServiceBean> serviceBeans = Arrays.asList(serviceBean);
    List<SubService> subServices = Arrays.asList(subService);
    List<ReasonDetailSubService> reasonDetailSubServices = Arrays.asList(reasonDetailSubService);
    Page<SubService> subServicesPage = new PageImpl<>(subServices, pageable, subServices.size());

    @Test
    public void serviceList() {
        Mockito.when(serviceDAO.serviceList()).thenReturn(serviceBeans);
        List<ServiceResponse> responses = new ArrayList<>();
        serviceBeans.forEach(s -> responses.add(covertServiceResponse(s)));
        subServiceServiceImpl.serviceList();
    }

    @Test
    public void searchService() {
        List<SubServiceResponse> subServices = new ArrayList<>();
        Mockito.when(serviceDAO.serviceList()).thenReturn(serviceBeans);
        Mockito.when(subServiceDAO.searchSubServiceBySubServiceName(Mockito.any(), Mockito.anyObject())).thenReturn(subServicesPage);
        Mockito.when(subServiceDAO.searchSubServiceBySubServiceNameAndServiceId(Mockito.any(), Mockito.any(), Mockito.anyObject())).thenReturn(subServicesPage);
        for (SubService ad : subServicesPage) {
            SubServiceResponse response = covertSubServiceResponse(ad);
            subServices.add(response);
        }
        subServiceServiceImpl.searchService(1, 5, 1L, "aaa");
        subServiceServiceImpl.searchService(1, 5, -1L, "aaa");
    }

    @Test
    public void reasonDetailServiceBySubServiceIdTestSuccess() {
        Mockito.when(subServiceDAO.getOne(Mockito.any())).thenReturn(subService);
        Mockito.when(reasonDetailSubServiceDAO.getListReasonBySubServiceId(Mockito.any())).thenReturn(reasonDetailSubServices);
        List<DetailSubServiceResponse> listResponse = new ArrayList<>();
        reasonDetailSubServices.forEach(s -> listResponse.add(covertDetailServiceResponse(s)));
        subServiceServiceImpl.reasonDetailServiceBySubServiceId(1L);
    }

    @Test
    public void reasonDetailServiceBySubServiceIdTestFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        subServiceServiceImpl.reasonDetailServiceBySubServiceId(id);
    }

    @Test
    public void reasonDetailServiceBySubServiceIdTestFailByServiceNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Không tồn tại loại dịch vụ này");
        Long id = 1L;
        Mockito.when(subServiceDAO.getOne(Mockito.any())).thenReturn(null);
        subServiceServiceImpl.reasonDetailServiceBySubServiceId(id);
    }

    @Test
    public void reasonDetailServiceBySubServiceIdTestFailByReasonServiceNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Chi tiết dịch vụ không tồn tại");
        Long id = 1L;
        List<ReasonDetailSubService> reasonDetailSubServices = new ArrayList<>();
        Mockito.when(subServiceDAO.getOne(Mockito.any())).thenReturn(subService);
        Mockito.when(reasonDetailSubServiceDAO.getListReasonBySubServiceId(Mockito.any())).thenReturn(reasonDetailSubServices);
        subServiceServiceImpl.reasonDetailServiceBySubServiceId(id);
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
            response.setPrice(HelperUtils.formatDoubleNUmber(service.getPrice()));
        }
        return response;
    }

}
