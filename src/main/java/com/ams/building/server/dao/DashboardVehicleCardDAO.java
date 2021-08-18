package com.ams.building.server.dao;

import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.response.DashboardResponseTotal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardVehicleCardDAO extends CrudRepository<VehicleCard, Long> {

    @Query(value = "SELECT month(v.start_date) AS date ,count(v.id) AS total FROM vehicle_card v WHERE v.status_vehicle_card_id = 3" +
            "    Group by month(v.start_date)", nativeQuery = true)
    List<DashboardResponseTotal> monthlyVehicle();

}
