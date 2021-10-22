package com.ams.building.server.dao;

import com.ams.building.server.bean.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.account.name LIKE CONCAT('%',:name,'%') ORDER BY f.id DESC")
    Page<Feedback> findFeedbacksByName(@Param("name") String name, Pageable pageable);

}
