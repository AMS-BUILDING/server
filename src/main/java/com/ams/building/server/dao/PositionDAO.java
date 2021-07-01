package com.ams.building.server.dao;

import com.ams.building.server.bean.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionDAO extends JpaRepository<Position, Long> {

    @Query("SELECT p FROM Position p WHERE p.show = ?1")
    List<Position> positionsByShow(Boolean show);

}
