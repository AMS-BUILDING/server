package com.ams.building.server.dao;

import com.ams.building.server.bean.DetailApartmentBilling;
import com.ams.building.server.response.ServiceNameFeeApartmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailApartmentBuildingDAO extends JpaRepository<DetailApartmentBilling, Long> {

    @Query("SELECT d FROM DetailApartmentBilling d WHERE d.account.id=?1 AND d.billingMonth=?2 ORDER BY d.id")
    List<DetailApartmentBilling> detailApartmentBillingByAccountIdAndMonth(Long accountId, String billingMonth);

    @Query("SELECT SUM(d.vehiclePrice) FROM DetailApartmentBilling d WHERE d.account.id=?1 AND d.billingMonth=?2 ORDER BY d.id")
    Double feeVehicleCardByAccountIdAndMonth(Long accountId, String billingMonth);

    @Query("SELECT SUM(d.residentPrice) FROM DetailApartmentBilling d WHERE d.account.id=?1 AND d.billingMonth=?2 ORDER BY d.id")
    Double feeResidentCardByAccountIdAndMonth(Long accountId, String billingMonth);

    @Query("SELECT new com.ams.building.server.response.ServiceNameFeeApartmentResponse(dab.subServiceName,dab.subServicePrice) FROM DetailApartmentBilling dab WHERE dab.account.id=?1 AND dab.billingMonth=?2  ORDER BY dab.id ")
    List<ServiceNameFeeApartmentResponse> feeServiceNameByAccountIdAndMonth(Long accountId, String month);

}
