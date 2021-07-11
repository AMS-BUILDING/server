package com.ams.building.server.dao;

import com.ams.building.server.bean.VehicleCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCardDAO extends JpaRepository<VehicleCard, Long> {

    @Query("SELECT v FROM VehicleCard v WHERE v.account.name LIKE CONCAT('%',:vehicleName,'%') AND v.account.phone LIKE CONCAT('%',:phoneNumber,'%') AND v.licensePlate LIKE CONCAT('%',:licensePlates,'%') ORDER BY v.id")
    Page<VehicleCard> vehicleCardListWithoutStatus(@Param("vehicleName") String vehicleName, @Param("phoneNumber") String phoneNumber, @Param("licensePlates") String licensePlates, Pageable pageable);

    @Query("SELECT v FROM VehicleCard v WHERE v.account.name LIKE CONCAT('%',:vehicleName,'%') AND v.account.phone LIKE CONCAT('%',:phoneNumber,'%') AND v.licensePlate LIKE CONCAT('%',:licensePlates,'%') AND v.statusVehicleCard.id =:status ORDER BY v.id")
    Page<VehicleCard> vehicleCardListWithStatus(@Param("vehicleName") String vehicleName, @Param("phoneNumber") String phoneNumber, @Param("licensePlates") String licensePlates, @Param("status") Long status, Pageable pageable);

    @Query("SELECT v FROM VehicleCard v WHERE v.id=?1")
    VehicleCard getVehicleCardById(Long id);

    @Query("UPDATE VehicleCard r SET r.statusVehicleCard.id=?1 WHERE r.id=?2")
    void updateStatus(Long id, Long statusId);

}
