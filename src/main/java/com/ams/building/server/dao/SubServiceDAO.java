package com.ams.building.server.dao;

import com.ams.building.server.bean.SubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServiceDAO extends JpaRepository<SubService, Long> {

    @Query("SELECT s FROM SubService s WHERE s.subServiceName LIKE CONCAT('%',:subServiceName,'%')  ORDER BY s.id")
    Page<SubService> searchSubServiceBySubServiceName(@Param("subServiceName") String subServiceName, Pageable pageable);

    @Query("SELECT s FROM SubService s WHERE s.subServiceName LIKE CONCAT('%',:subServiceName,'%') AND s.service.id =:serviceId ORDER BY s.id")
    Page<SubService> searchSubServiceBySubServiceNameAndServiceId(@Param("subServiceName") String subServiceName, @Param("serviceId") Long serviceId, Pageable pageable);

}
