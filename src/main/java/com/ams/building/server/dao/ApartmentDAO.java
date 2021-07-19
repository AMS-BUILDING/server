package com.ams.building.server.dao;

import com.ams.building.server.bean.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentDAO extends JpaRepository<Apartment, Long> {

    @Query("SELECT a FROM Apartment a WHERE a.id = ?1")
    Apartment getApartmentById(Long id);

    @Query("SELECT a FROM Apartment a WHERE a.account.id =?1 AND a.account.role.name=?2")
    Apartment getApartmentByAccountId(Long accountId, String role);

    @Query("SELECT a FROM Apartment a WHERE a.account.name LIKE CONCAT('%',:householderName,'%') AND a.roomNumber.roomName LIKE CONCAT('%',:roomNumber,'%') AND a.account.enabled = true AND a.account.role.name ='ROLE_LANDLORD' ORDER BY a.id")
    Page<Apartment> searchApartmentByRoomNumberHouseholderName(@Param("householderName") String householderName, @Param("roomNumber") String roomNumber, Pageable pageable);

    @Query("SELECT  a FROM Apartment a WHERE a.account.name LIKE CONCAT('%',:name,'%') AND a.roomNumber.roomName LIKE CONCAT('%',:roomNumber,'%') AND a.account.phone LIKE CONCAT('%',:phone,'%') ORDER BY a.account.name ASC ")
    Page<Apartment> searchResidentByNameRoomNumberAndPhone(@Param("name") String name, @Param("roomNumber") String roomNumber, @Param("phone") String phone, Pageable pageable);

    @Query("SELECT a FROM Apartment a WHERE a.account.id = :accountId AND a.id = :apartmentId")
    Apartment getAccountDetail(@Param("accountId") Long accountId, @Param("apartmentId") Long apartmentId);

    @Query("SELECT a FROM Apartment a WHERE a.roomNumber.floorBlock.block.id = :blockId AND a.roomNumber.floorBlock.floor.id = :floorId AND a.account IS NULL")
    List<Apartment> searchRoomNumberByBlockAndFloorNullAccount(@Param("blockId") Long blockId, @Param("floorId") Long floorId);

    @Query("SELECT a FROM Apartment a WHERE a.roomNumber.id = :roomNumberId")
    List<Apartment> searchAccountByRoomNumberId(@Param("roomNumberId") Long apartmentId);

}
