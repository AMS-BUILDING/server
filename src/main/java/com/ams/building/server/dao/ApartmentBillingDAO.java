package com.ams.building.server.dao;

import com.ams.building.server.bean.ApartmentBilling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentBillingDAO extends JpaRepository<ApartmentBilling, Long> {


    @Query("SELECT ab FROM ApartmentBilling ab WHERE ab.billingMonth=?1 ORDER BY ab.id")
    Page<ApartmentBilling> searchApartmentBillingByMonth(String month, Pageable pageable);

}
