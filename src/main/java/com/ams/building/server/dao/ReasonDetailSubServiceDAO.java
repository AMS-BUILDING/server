package com.ams.building.server.dao;

import com.ams.building.server.bean.ReasonDetailSubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReasonDetailSubServiceDAO extends JpaRepository<ReasonDetailSubService, Long> {

    @Query("SELECT r FROM ReasonDetailSubService r WHERE r.detailSubService.service.id =?1")
    List<ReasonDetailSubService> getListReasonBySubServiceId(Long subServiceId);

    @Query("SELECT r FROM  ReasonDetailSubService r WHERE r.id = ?1 ")
    ReasonDetailSubService getReasonDetailSubServiceById(Long id);

    @Query("SELECT r FROM  ReasonDetailSubService r WHERE r.detailSubService.id = ?1")
    List<ReasonDetailSubService> getListReasonByDetailSubServiceId(Long id);
}
