package com.ams.building.server.dao;

import com.ams.building.server.bean.Account;
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
public interface AccountDAO extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.name LIKE CONCAT('%',:name,'%') AND a.phone LIKE CONCAT('%',:phoneNumber,'%') AND a.identifyCard LIKE CONCAT('%',:identifyCard,'%') AND a.role.name IN (:roles) AND a.position.id =:position  ORDER BY a.id DESC")
    Page<Account> searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(@Param("name") String name, @Param("phoneNumber")
            String phoneNumber, @Param("identifyCard") String identifyCard, @Param("position") Long positionId, @Param("roles") List<String> roles, Pageable pageable);

    @Query(" SELECT a FROM Account a WHERE a.name LIKE CONCAT('%',:name,'%') AND a.phone LIKE CONCAT('%',:phoneNumber,'%') AND a.identifyCard LIKE CONCAT('%',:identifyCard,'%') AND a.role.name IN (:roles) ORDER BY a.id DESC")
    Page<Account> searchAccountByNamePhoneIdentifyCardAndRole(@Param("name") String name, @Param("phoneNumber")
            String phoneNumber, @Param("identifyCard") String identifyCard, @Param("roles") List<String> roles, Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM Account a WHERE a.id=?1")
    void removeAccount(Long id);

    @Query("SELECT a FROM Account  a WHERE a.identifyCard =?1")
    Account getAccountByIdentify(String identifyCard);

    @Query("SELECT a FROM Account  a WHERE a.email =?1")
    Account getAccountByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.id =?1")
    Account getAccountById(Long id);

    @Query("SELECT a FROM Account a WHERE a.role.id IN (1,2)")
    List<Account> managementAccount();

    @Query("SELECT a FROM Account a WHERE a.email IN (:emails) ORDER BY a.id DESC")
    List<Account> getAccountByListEmail(@Param("emails") List<String> emails);

    @Query("SELECT a FROM Account a WHERE a.identifyCard IN (:identifyCards) ORDER BY a.id DESC")
    List<Account> getAccountByListIdentifyCard(@Param("identifyCards") List<String> identifyCards);

    @Query("SELECT a FROM Account a WHERE a.phone IN (:phoneNumber) ORDER BY a.id DESC")
    List<Account> getAccountByPhoneNumberList(@Param("phoneNumber") List<String> phoneNumber);

    @Query("SELECT count(a.id) FROM Account a where a.role.id IN(3,5) ")
    Long countAccountEnable();

    @Query("SELECT a.phone FROM Account a WHERE a.phone=?1")
    List<String> getAccountByPhoneNumber(String phoneNumber);

    @Query("SELECT a FROM Account a WHERE a.role.id IN (3,5)")
    List<Account> getAccountByRoles();


    @Query("SELECT a FROM Account a WHERE a.role.id = 3")
    List<Account> getAccountByRoleLandol();

}
