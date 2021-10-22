package com.ams.building.server.dao;

import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.response.FloorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorBlockDAO extends JpaRepository<FloorBlock, Long> {

    @Query("SELECT distinct new com.ams.building.server.response.FloorResponse(a.roomNumber.floorBlock.floor.id,a.roomNumber.floorBlock.floor.floorName) " +
            " FROM Apartment a WHERE  a.account IS NULL AND a.roomNumber.floorBlock.block.id=?1  ")
    List<FloorResponse> floorBlockByBlockId(Long blockId);

}
