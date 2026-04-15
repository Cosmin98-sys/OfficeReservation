package com.officereservation.reservationservice.core.messaging.publishers;

import com.officereservation.reservationservice.config.RabbitMQConfig;
import com.officereservation.reservationservice.core.messaging.events.ReservationCancelledEvent;
import com.officereservation.reservationservice.core.messaging.events.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishReservationCreated(ReservationCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVATION_EXCHANGE,
                RabbitMQConfig.RESERVATION_CREATED_ROUTING_KEY,
                event,
                withMessageId()
        );
    }

    public void publishReservationCancelled(ReservationCancelledEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVATION_EXCHANGE,
                RabbitMQConfig.RESERVATION_CANCELLED_ROUTING_KEY,
                event,
                withMessageId()
        );
    }

    private MessagePostProcessor withMessageId(){
        return message -> {
            message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
            return message;
        };
    }
}