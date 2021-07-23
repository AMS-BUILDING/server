package com.ams.building.server.dao;

import com.ams.building.server.bean.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorDAO extends JpaRepository<Floor, Long> {
}
