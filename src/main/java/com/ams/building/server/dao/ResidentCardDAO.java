package com.ams.building.server.dao;

import com.ams.building.server.bean.ResidentCard;
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
public interface ResidentCardDAO extends JpaRepository<ResidentCard, Long> {

    @Query("SELECT r FROM ResidentCard r WHERE r.account.id=?1 AND r.billingMonth = ?2 ORDER BY r.id")
    Page<ResidentCard> searchResidentCardByAccountId(Long accountId, String billingMonth, Pageable pageable);

    @Query("SELECT r FROM ResidentCard  r WHERE r.id=?1")
    ResidentCard getDetailResidentCardById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ResidentCard r SET r.statusResidentCard.id=:statusId WHERE r.id=:cardId")
    void updateStatus(@Param("statusId") Long statusId, @Param("cardId") Long cardId);

    @Query("SELECT r FROM ResidentCard r WHERE r.account.id =:accountId AND r.statusResidentCard.id IN (:status) ORDER BY r.id DESC")
    List<ResidentCard> residentCardRegister(@Param("accountId") Long accountId, @Param("status") List<Long> status);

}
