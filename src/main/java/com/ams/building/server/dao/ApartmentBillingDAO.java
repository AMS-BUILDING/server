package com.ams.building.server.dao;

import com.ams.building.server.bean.ApartmentBilling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApartmentBillingDAO extends JpaRepository<ApartmentBilling, Long> {

    @Query("SELECT a FROM ApartmentBilling a WHERE a.apartment.id =?1")
    List<ApartmentBilling> detailApartmentBuildingByMonth(Long apartmentId);

    @Query("SELECT a FROM ApartmentBilling a WHERE a.billingMonth =?1 AND a.apartment.account.id =?2 ")
    ApartmentBilling getDetailByMonth(String billingMonth, Long accountId);

    @Query("SELECT ab FROM ApartmentBilling ab WHERE ab.billingMonth=?1 ORDER BY ab.id")
    Page<ApartmentBilling> searchApartmentBillingByMonth(String month, Pageable pageable);

    @Query("SELECT a FROM ApartmentBilling a WHERE a.billingMonth =?1")
    List<ApartmentBilling> listApartmentBillingByMonth(String billingMonth);

    @Query("SELECT a FROM ApartmentBilling a WHERE a.billingMonth =?1 AND a.statusApartmentBilling.id=1")
    List<ApartmentBilling> listApartmentBillingByMonthNotPayment(String billingMonth);

    @Query("SELECT COUNT(a.id) FROM ApartmentBilling a WHERE a.isRead = false AND a.apartment.account.id=?1")
    Integer countNotificationNotReadPrivate(Long accountId);

    @Query("SELECT a FROM ApartmentBilling a WHERE a.isRead = false AND a.apartment.account.id=?1")
    List<ApartmentBilling> listApartmentBillingNotRead(Long accountId);

    @Query("SELECT a FROM ApartmentBilling a WHERE a.id=?1 ")
    ApartmentBilling getDetailApartmentBilling(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ApartmentBilling r SET r.statusApartmentBilling.id=:statusId WHERE r.id=:id ")
    void updateStatusBilling(@Param("statusId") Long statusId, @Param("id") Long id);


}
