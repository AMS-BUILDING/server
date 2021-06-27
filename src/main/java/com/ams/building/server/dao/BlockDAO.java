package com.ams.building.server.dao;

import com.ams.building.server.bean.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockDAO extends JpaRepository<Block, Long> {
}
