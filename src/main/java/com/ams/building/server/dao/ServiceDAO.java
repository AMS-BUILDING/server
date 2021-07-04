package com.ams.building.server.dao;

import com.ams.building.server.bean.ServiceBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceDAO extends JpaRepository<ServiceBean, Long> {

    @Query("SELECT s FROM ServiceBean s ORDER BY s.id")
    List<ServiceBean> serviceList();

}
