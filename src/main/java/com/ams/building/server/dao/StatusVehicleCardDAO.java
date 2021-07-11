package com.ams.building.server.dao;

import com.ams.building.server.bean.StatusVehicleCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusVehicleCardDAO extends JpaRepository<StatusVehicleCard, Long> {
}
