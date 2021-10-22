package com.ams.building.server.dao;

import com.ams.building.server.bean.DetailSubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DetailSubServiceDAO extends JpaRepository<DetailSubService, Long> {

    @Query("SELECT d FROM DetailSubService d WHERE d.service.id = ?1  ORDER BY d.id DESC")
    List<DetailSubService> getDetailSubServiceBySubServiceId(Long subServiceId);

}
