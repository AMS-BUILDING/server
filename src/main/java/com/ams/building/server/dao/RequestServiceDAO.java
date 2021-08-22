package com.ams.building.server.dao;

import com.ams.building.server.bean.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface RequestServiceDAO extends JpaRepository<RequestService, Long> {

    @Query("SELECT r FROM RequestService r WHERE r.account.name LIKE CONCAT('%',:accountName,'%') AND r.reasonDetailSubService.reasonName LIKE CONCAT('%',:serviceName,'%') ORDER BY r.id")
    Page<RequestService> requestServicesNotStatus(@Param("accountName") String accountName, @Param("serviceName") String serviceName, Pageable pageable);

    @Query("SELECT r FROM RequestService r WHERE r.account.name LIKE CONCAT('%',:accountName,'%') AND r.reasonDetailSubService.reasonName LIKE CONCAT('%',:serviceName,'%') AND r.statusServiceRequest.id =:statusId ORDER BY r.id")
    Page<RequestService> requestServicesWithStatus(@Param("accountName") String accountName, @Param("serviceName") String serviceName, @Param("statusId") Long status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE RequestService r SET r.statusServiceRequest.id=?1 WHERE r.id=?2")
    void updateStatus(Long statusId, Long requestId);

    @Query("SELECT r FROM RequestService  r WHERE r.id=?1")
    RequestService findRequestServiceById(Long id);

    @Query("SELECT r FROM  RequestService r WHERE r.startDate = :startDate AND r.reasonDetailSubService.id = :id")
    RequestService findRequestServiceByStartDateAndReasonDetailSubServiceId(@Param("startDate") Date startDate, @Param("id") Long id);

    @Query("SELECT r FROM  RequestService r WHERE r.account.id = :accountId AND r.reasonDetailSubService.id >= 6 AND r.statusServiceRequest.id NOT IN (3,4) ORDER BY r.id DESC ")
    List<RequestService> findRequestServiceByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT r FROM RequestService r WHERE r.account.id=?1 AND r.statusServiceRequest.id=?2 AND r.statusServiceRequest.id < 4 ORDER BY r.startDate DESC ")
    List<RequestService> requestServiceByAccountId(Long accountId, Long statusId);

    @Query("SELECT sum(r.reasonDetailSubService.price) FROM RequestService r where r.statusServiceRequest.id = 3")
    Double totalRevenue();

    @Query("SELECT count(r.id)FROM RequestService r WHERE r.statusServiceRequest.id = 3")
    Long totalServiceRequest();

    @Query("SELECT r FROM RequestService  r WHERE r.statusServiceRequest.id=3 AND MONTH(r.dateRequest)=?1 AND YEAR(r.dateRequest)=?2 ORDER BY r.id  ")
    List<RequestService> requestServiceByMonth(String month, String year);

}
