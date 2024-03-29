package com.ams.building.server.dao;

import com.ams.building.server.bean.Notification;
import com.ams.building.server.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationDAO extends JpaRepository<Notification, Long> {

    @Query("SELECT distinct new com.ams.building.server.response.NotificationResponse(n.title,n.description) FROM Notification n WHERE n.title LIKE CONCAT('%',:title,'%')")
    Page<NotificationResponse> searchNotificationByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT distinct n FROM Notification  n  WHERE n.account.id =?1 ORDER BY n.id desc")
    List<Notification> listNotification(Long accountId);

    @Query("SELECT distinct n FROM Notification  n WHERE n.isRead = false  AND n.account.id =?1 ORDER BY n.id desc")
    List<Notification> listNotificationNotRead(Long accountId);

    @Query("SELECT COUNT(n.id) FROM Notification n WHERE n.isRead = false AND n.account.id =?1 ")
    Integer countNotificationNotRead(Long accountId);

    @Query("SELECT distinct n FROM Notification  n WHERE n.account.id =?1 ORDER BY n.id desc")
    List<Notification> listNotificationByAccountId(Long accountId);

}
