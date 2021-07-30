package com.ams.building.server.dao;

import com.ams.building.server.bean.DetailApartmentBilling;
import com.ams.building.server.response.BillingCardTotalResponse;
import com.ams.building.server.response.BillingResidentCardResponse;
import com.ams.building.server.response.BillingServiceResponse;
import com.ams.building.server.response.BillingVehicleCardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailApartmentBillingDAO extends JpaRepository<DetailApartmentBilling, Long> {

    @Query("SELECT new com.ams.building.server.response.BillingServiceResponse(dab.id,dab.apartmentBilling.apartment.roomNumber.floorBlock.block.blockName,dab.apartmentBilling.apartment.roomNumber.roomName,dab.reasonDetailSubService.detailSubService.service.subServiceName,dab.reasonDetailSubService.price,dab.billingMonth) FROM DetailApartmentBilling dab WHERE dab.billingMonth=?1 GROUP BY dab.billingMonth,dab.account.id ORDER BY dab.id ")
    Page<BillingServiceResponse> searchBillingDetailAboutServiceByMonth(String month, Pageable pageable);

    @Query("SELECT new com.ams.building.server.response.BillingCardTotalResponse(dab.id,dab.account.id,dab.apartmentBilling.apartment.roomNumber.floorBlock.block.blockName,dab.apartmentBilling.apartment.roomNumber.roomName,SUM(dab.vehiclePrice),SUM(dab.residentPrice),dab.billingMonth) FROM DetailApartmentBilling dab WHERE dab.billingMonth=?1 GROUP BY dab.billingMonth,dab.account.id ORDER BY dab.id ")
    Page<BillingCardTotalResponse> searchBillingDetailAboutCardByMonth(String month, Pageable pageable);

    @Query("SELECT new com.ams.building.server.response.BillingResidentCardResponse(dab.residentCard.id,dab.residentCard.cardCode) FROM DetailApartmentBilling dab WHERE dab.account.id=?1 AND dab.billingMonth=?2  ORDER BY dab.id ")
    Page<BillingResidentCardResponse> searchBillingDetailAboutResidentCardByAccountIdAndMonth(Long accountId, String month, Pageable pageable);

    @Query("SELECT new com.ams.building.server.response.BillingVehicleCardResponse(dab.vehicleCard.id,dab.account.id,dab.vehicleCard.vehicle.id,dab.apartmentBilling.apartment.roomNumber.floorBlock.block.blockName,dab.apartmentBilling.apartment.roomNumber.roomName,dab.vehicleCard.vehicle.vehicleName,COUNT(dab.vehicleCard.vehicle.id) , SUM(dab.vehiclePrice)) FROM DetailApartmentBilling dab WHERE dab.account.id=?1 AND dab.billingMonth=?2  ORDER BY dab.id ")
    Page<BillingVehicleCardResponse> searchBillingDetailAboutVehicleCardByAccountIdAndMonth(Long accountId, String month, Pageable pageable);

}
