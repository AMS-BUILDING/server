package com.ams.building.server.dao;

import com.ams.building.server.bean.NotificationBuilding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDAO extends JpaRepository<NotificationBuilding, Long> {

    @Query("SELECT n FROM NotificationBuilding n WHERE n.titleNotification LIKE CONCAT('%',:title,'%') ORDER BY n.id")
    Page<NotificationBuilding> searchNotificationByTitle(@Param("title") String title, Pageable pageable);

}
