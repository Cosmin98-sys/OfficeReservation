package com.officereservation.notificationservice.dataaccess.notification;

import com.officereservation.notificationservice.model.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> getAllByUserId(Long userId, Pageable pageable);
}