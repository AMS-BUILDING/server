//package com.ams.building.server.service;
//
//import com.ams.building.server.bean.AbsentDetail;
//import com.ams.building.server.dao.AbsentDetailDAO;
//import com.ams.building.server.dao.AbsentTypeDAO;
//import com.ams.building.server.dao.ApartmentDAO;
//import com.ams.building.server.service.impl.AbsentServiceImpl;
//import org.hamcrest.MatcherAssert;
//import org.hamcrest.Matchers;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.springframework.data.domain.Page;
//
//import java.util.Collections;
//import java.util.List;
//
//@RunWith(PowerMockRunner.class)
//public class AbsentServiceTest {
//
//    @InjectMocks
//    private AbsentServiceImpl absentService;
//
//    @Mock
//    private AbsentDetailDAO absentDao;
//
//    @Mock
//    private AbsentTypeDAO absentTypeDAO;
//
//    @Mock
//    ApartmentDAO apartmentDAO;
//
//    @Test
//    public void absentList() {
//        // Mock response
//        Page<AbsentDetail> absentDetails = null;
//        Mockito.when(absentDao.absentListNotByAbsentType(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
//                .thenReturn(absentDetails);
//
//        List<RakutenMonthlyBillingResponse> response = billingService.searchListRakutenMonthlyBill(new RakutenMonthlyBillingRequest());
//
//        MatcherAssert.assertThat(response.size(), Matchers.is(1));
//    }
//
//}
