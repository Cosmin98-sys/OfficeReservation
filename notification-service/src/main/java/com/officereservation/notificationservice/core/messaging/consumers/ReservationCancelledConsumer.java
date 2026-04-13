package com.officereservation.notificationservice.core.messaging.consumers;

import com.officereservation.notificationservice.dataaccess.notification.NotificationRepository;
import com.officereservation.notificationservice.core.messaging.events.ReservationCancelledEvent;
import com.officereservation.notificationservice.model.notification.Notification;
import com.officereservation.notificationservice.model.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationCancelledConsumer {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "notification.reservation.cancelled")
    public void consume(ReservationCancelledEvent event) {
        log.info("Received ReservationCancelledEvent for userId={}, reservationId={}", event.getUserId(), event.getReservationId());

        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .message(String.format("Your reservation for workstation '%s' on %s has been cancelled.",
                        event.getWorkstationName(), event.getDate()))
                .type(NotificationType.RESERVATION_CANCELLED)
                .build();

        notificationRepository.save(notification);
    }
}