package com.ams.building.server.dao;

import com.ams.building.server.bean.FloorBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorBlockDAO extends JpaRepository<FloorBlock, Long> {
}
