package com.ams.building.server.dao;

import com.ams.building.server.bean.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentDAO extends JpaRepository<Apartment, Long> {

    @Query("SELECT a FROM Apartment a WHERE a.account.id =?1 AND a.account.role.name=?2")
    Apartment getApartmentByAccountId(Long accountId, String role);

}
