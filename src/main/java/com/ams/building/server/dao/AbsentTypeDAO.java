package com.ams.building.server.dao;

import com.ams.building.server.bean.AbsentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsentTypeDAO extends JpaRepository<AbsentType, Long> {

    @Query("SELECT a FROM AbsentType a WHERE a.id =?1")
    AbsentType findAbsentTypeById(Long id);

}
