package com.officereservation.reservationservice.core.messaging.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.officereservation.reservationservice.config.RabbitMQConfig;
import com.officereservation.reservationservice.core.messaging.events.ReservationCancelledEvent;
import com.officereservation.reservationservice.core.messaging.events.ReservationCreatedEvent;
import com.officereservation.reservationservice.dataaccess.outbox.OutboxMessageRepository;
import com.officereservation.reservationservice.model.outbox.OutboxMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationEventPublisher {
    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper;

    public void publishReservationCreated(ReservationCreatedEvent event){
        saveToOutbox(event,RabbitMQConfig.RESERVATION_CREATED_ROUTING_KEY);
    }

    public void publishReservationCancelled(ReservationCancelledEvent event){
        saveToOutbox(event,RabbitMQConfig.RESERVATION_CANCELLED_ROUTING_KEY);
    }

    private void saveToOutbox(Object event, String routingKey){
        try{
            String payload = objectMapper.writeValueAsString(event);
            outboxMessageRepository.save(OutboxMessage.builder()
                            .eventType(event.getClass().getName())
                            .routingKey(routingKey)
                            .payload(payload)
                            .build());
        }catch (JsonProcessingException e){
            throw new RuntimeException("Failed to serialize event to outbox", e);
        }
    }
}