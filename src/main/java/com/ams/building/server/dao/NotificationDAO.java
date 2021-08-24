package com.ams.building.server.dao;

import com.ams.building.server.bean.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationDAO extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.title LIKE CONCAT('%',:title,'%') ORDER BY n.id")
    Page<Notification> searchNotificationByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT n FROM Notification  n ORDER BY n.id desc")
    List<Notification> listNotification();

    @Query("SELECT n FROM Notification  n WHERE n.isRead = false  AND n.account.id =?1 ORDER BY n.id desc")
    List<Notification> listNotificationNotRead(Long accountId);

    @Query("SELECT COUNT(n.id) FROM Notification n WHERE n.isRead = false AND n.account.id =?1 ")
    Integer countNotificationNotRead(Long accountId);

    @Query("SELECT n FROM Notification  n WHERE n.account.id =?1 ORDER BY n.id desc")
    List<Notification> listNotificationByAccountId(Long accountId);

}
