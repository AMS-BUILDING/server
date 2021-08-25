package com.ams.building.server.dao;

import com.ams.building.server.bean.RequestService;
import com.ams.building.server.response.DashboardResponseNumberOfUseServiceRequest;
import com.ams.building.server.response.DashboardResponseTotal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRequestServiceDAO extends CrudRepository<RequestService, Long> {

    @Query(value = "SELECT year (s.start_date) AS date,sum(r.price) AS total FROM service_request s" +
            " join reason_detail_sub_service r" +
            "   on s.reason_detail_sub_service_id = r.id" +
            "    Group by year(s.start_date)", nativeQuery = true)
    List<DashboardResponseTotal> yearlyTotalRevenue();

    @Query(value = "SELECT year (s.start_date) AS date ,count(s.id) AS total FROM service_request s" +
            "    Group by year (s.start_date)", nativeQuery = true)
    List<DashboardResponseTotal> yearlyCountServiceRequest();

    @Query(value = "SELECT ss.sub_service_name AS ServiceName, count(s.id) AS Total FROM service_request s" +
            " join reason_detail_sub_service r " +
            "    on s.reason_detail_sub_service_id = r.id" +
            "     join detail_sub_service d " +
            "    on r.detail_sub_service_id = d.id" +
            "     join sub_service ss " +
            "    on d.sub_service_id = ss.id" +
            "    group by ss.sub_service_name", nativeQuery = true)
    List<DashboardResponseNumberOfUseServiceRequest> numberOfUseServiceRequest();

}
