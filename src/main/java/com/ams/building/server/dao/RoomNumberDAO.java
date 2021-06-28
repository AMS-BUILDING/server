package com.ams.building.server.dao;

import com.ams.building.server.bean.RoomNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomNumberDAO extends JpaRepository<RoomNumber,Long> {
}
