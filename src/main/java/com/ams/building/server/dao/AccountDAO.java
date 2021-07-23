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

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {

    Account getAccountByEmail(String email);

    Account getAccountById(Long id);

    @Query("SELECT a FROM Account a WHERE a.name LIKE CONCAT('%',:name,'%') AND a.phone LIKE CONCAT('%',:phoneNumber,'%') AND a.identifyCard LIKE CONCAT('%',:identityCard,'%') AND a.role.name IN :roles AND a.position.id =:position  ORDER BY a.id")
    Page<Account> searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(@Param("name") String name, @Param("phoneNumber")
            String phoneNumber, @Param("identityCard") String identityCard, @Param("position") Long positionId, @Param("roles") String roles, Pageable pageable);

    @Query(" SELECT a FROM Account a WHERE a.name LIKE CONCAT('%',:name,'%') AND a.phone LIKE CONCAT('%',:phoneNumber,'%') AND a.identifyCard LIKE CONCAT('%',:identityCard,'%') AND a.role.name IN :roles ORDER BY a.id")
    Page<Account> searchAccountByNamePhoneIdentifyCardAndRole(@Param("name") String name, @Param("phoneNumber")
            String phoneNumber, @Param("identityCard") String identityCard, @Param("roles") String roles, Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM Account a WHERE a.id=?1")
    void removeAccount(Long id);

    @Query("SELECT a FROM Account  a WHERE a.id =?1 AND a.role.name = ?2")
    Account getAccountByIdAndRole(Long accountId, String role);

    @Query("SELECT a FROM Account  a WHERE a.identifyCard =?1 AND a.role.name = ?2")
    Account getAccountByIdentifyAndRole(String identifyCard, String role);

    @Query("SELECT a FROM Account  a WHERE a.email =?1 AND a.role.name = ?2")
    Account getAccountByEmailAndRole(String email, String role);

}
