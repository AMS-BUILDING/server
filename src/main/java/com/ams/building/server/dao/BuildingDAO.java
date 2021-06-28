package com.ams.building.server.dao;

import com.ams.building.server.bean.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingDAO extends JpaRepository<Building,Long> {
}
