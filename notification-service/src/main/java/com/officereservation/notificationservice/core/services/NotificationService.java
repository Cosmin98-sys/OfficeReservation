package com.officereservation.notificationservice.core.services;

import com.officereservation.notificationservice.core.dtos.responses.GetAllNotificationsByUserIdResponse;
import com.officereservation.notificationservice.dataaccess.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Page<GetAllNotificationsByUserIdResponse> getAllNotificationsByUserId(Long userId, Pageable pageable) {
        return notificationRepository.getAllByUserId(userId, pageable)
                .map(n -> new GetAllNotificationsByUserIdResponse(n.getId(), n.getMessage(),
                        n.getType(), n.isRead(), GetAllNotificationsByUserIdResponse.formatDateTime(n.getCreatedAt())));
    }
}