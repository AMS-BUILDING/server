package com.ams.building.server.dao;

import com.ams.building.server.bean.VehicleCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCardDAO extends JpaRepository<VehicleCard, Long> {
}
