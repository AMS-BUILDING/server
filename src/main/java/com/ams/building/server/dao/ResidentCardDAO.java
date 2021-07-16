package com.ams.building.server.dao;

import com.ams.building.server.bean.ResidentCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentCardDAO extends JpaRepository<ResidentCard, Long> {

    @Query("SELECT r FROM ResidentCard r WHERE r.account.id=?1 ORDER BY r.id")
    Page<ResidentCard> searchResidentCardByAccountId(Long accountId, Pageable pageable);

    @Query("SELECT r FROM ResidentCard  r WHERE r.id=?1")
    ResidentCard getDetailResidentCardById(Long id);

    @Query("UPDATE ResidentCard r SET r.statusResidentCard.id=:statusId WHERE r.id=:cardId")
    void updateStatus(@Param("statusId") Long statusId, @Param("cardId") Long cardId);

}
