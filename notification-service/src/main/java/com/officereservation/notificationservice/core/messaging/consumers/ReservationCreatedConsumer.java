package com.officereservation.notificationservice.core.messaging.consumers;

import com.officereservation.notificationservice.core.messaging.events.ReservationCreatedEvent;
import com.officereservation.notificationservice.core.services.DeduplicationService;
import com.officereservation.notificationservice.dataaccess.notification.NotificationRepository;
import com.officereservation.notificationservice.model.notification.Notification;
import com.officereservation.notificationservice.model.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationCreatedConsumer {

    private final NotificationRepository notificationRepository;
    private final DeduplicationService deduplicationService;

    @RabbitListener(queues = "notification.reservation.created")
    public void consume(ReservationCreatedEvent event,
                        @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {
        if (deduplicationService.isDuplicate(messageId)) return;

        log.info("Received ReservationCreatedEvent for userId={}, reservationId={}", event.getUserId(), event.getReservationId());

        notificationRepository.save(Notification.builder()
                .userId(event.getUserId())
                .message(String.format("Your reservation for workstation '%s' on %s has been confirmed.",
                        event.getWorkstationName(), event.getDate()))
                .type(NotificationType.RESERVATION_CREATED)
                .build());
    }
}
