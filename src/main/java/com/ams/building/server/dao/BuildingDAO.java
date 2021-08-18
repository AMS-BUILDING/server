package com.ams.building.server.dao;

import com.ams.building.server.bean.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingDAO extends JpaRepository<Building, Long> {

    @Query("SELECT b FROM Building b WHERE b.id =?1")
    Building getDetailById(Long id);
}
