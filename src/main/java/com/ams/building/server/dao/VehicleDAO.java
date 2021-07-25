package com.ams.building.server.dao;

import com.ams.building.server.bean.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDAO extends JpaRepository<Vehicle, Long> {
}
