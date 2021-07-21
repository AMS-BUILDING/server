package com.ams.building.server.dao;

import com.ams.building.server.bean.FloorBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorBlockDAO extends JpaRepository<FloorBlock, Long> {

    @Query("SELECT fb FROM FloorBlock fb WHERE fb.block.id=?1 ORDER BY fb.id")
    List<FloorBlock> floorBlockByBlockId(Long blockId);

}
