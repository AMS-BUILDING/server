package com.ams.building.server.dao;

import com.ams.building.server.bean.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Long> {

    @Query("SELECT fb FROM Feedback fb WHERE fb.account.name LIKE CONCAT('%',:name,'%') AND fb.createdDate =:createdDate ORDER BY fb.id")
    Page<Feedback> findFeedbacksByNameAndCreateDate(@Param("name") String name, @Param("createdDate") Date createdDate, Pageable pageable);

    @Query("SELECT fb FROM Feedback fb WHERE fb.account.name LIKE CONCAT('%',:name,'%') ORDER BY fb.id")
    Page<Feedback> findFeedbacksByName(@Param("name") String name, Pageable pageable);

}
