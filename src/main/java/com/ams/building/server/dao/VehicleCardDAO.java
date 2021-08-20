package com.ams.building.server.dao;

import com.ams.building.server.bean.VehicleCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface VehicleCardDAO extends JpaRepository<VehicleCard, Long> {

    @Query("SELECT v FROM VehicleCard v WHERE v.account.name LIKE CONCAT('%',:vehicleName,'%') AND v.account.phone LIKE CONCAT('%',:phoneNumber,'%') AND v.licensePlate LIKE CONCAT('%',:licensePlates,'%')  AND v.billingMonth =:billingMonth AND v.isUse = 1 ORDER BY v.id")
    Page<VehicleCard> vehicleCardListWithoutStatus(@Param("vehicleName") String vehicleName, @Param("phoneNumber") String phoneNumber, @Param("licensePlates") String licensePlates, @Param("billingMonth") String billingMonth, Pageable pageable);

    @Query("SELECT v FROM VehicleCard v WHERE v.account.name LIKE CONCAT('%',:vehicleName,'%') AND v.account.phone LIKE CONCAT('%',:phoneNumber,'%') AND v.licensePlate LIKE CONCAT('%',:licensePlates,'%') AND v.billingMonth =:billingMonth AND v.isUse = 1 AND v.statusVehicleCard.id =:status ORDER BY v.id")
    Page<VehicleCard> vehicleCardListWithStatus(@Param("vehicleName") String vehicleName, @Param("phoneNumber") String phoneNumber, @Param("licensePlates") String licensePlates, @Param("billingMonth") String billingMonth, @Param("status") Long status, Pageable pageable);

    @Query("SELECT v FROM VehicleCard v WHERE v.id=?1 AND v.isUse = 1")
    VehicleCard getVehicleCardById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE VehicleCard r SET r.statusVehicleCard.id =:statusId WHERE r.id =:id AND r.isUse = 1")
    void updateStatus(@Param("statusId") Long statusId, @Param("id") Long id);

    @Query("SELECT v FROM VehicleCard v WHERE v.account.id =?1 AND v.vehicle.id=?2 AND v.statusVehicleCard.id =3 AND v.isUse = 1 ORDER BY v.id ")
    List<VehicleCard> vehicleListByAccountIdAndTypeId(Long accountId, Long vehicleTypeId);

    @Query("SELECT v FROM VehicleCard v WHERE v.account.id=?1 AND v.vehicle.id=?2  AND v.isUse = 1 ORDER BY v.id")
    Page<VehicleCard> searchVehicleCardByRoomNumber(Long accountId, Long vehicleId, Pageable pageable);

    @Query("SELECT v FROM VehicleCard v WHERE v.account.id =?1 AND v.statusVehicleCard.id <> 3 AND v.isUse = 1 ORDER BY v.id DESC")
    List<VehicleCard> vehicleCardRegister(Long accountId);

    @Query("SELECT v FROM VehicleCard v WHERE v.billingMonth=?1 AND v.isUse = 1 ORDER BY v.id DESC")
    List<VehicleCard> vehicleCardByBillingMonth(String billingMonth);

}
