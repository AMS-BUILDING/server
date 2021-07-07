package com.ams.building.server.dao;

import com.ams.building.server.bean.StatusServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRequestServiceDAO extends JpaRepository<StatusServiceRequest, Long> {

    List<StatusServiceRequest> requestStatusList();

}
