package com.ams.building.server.dao;

import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentBuildingDAO extends JpaRepository<Apartment, Long> {

    @Query("SELECT a FROM ApartmentBilling a WHERE a.apartment.id =?1")
    List<ApartmentBilling> detailApartmentBuildingByMonth(Long apartmentId);

    @Query("SELECT a FROM ApartmentBilling a WHERE a.billingMonth =?1 AND a.apartment.account.id =?2 ")
    ApartmentBilling getDetailByMonth(String billingMonth, Long accountId);

}
