package com.officereservation.reservationservice.core.messaging.relay;

import com.officereservation.reservationservice.config.RabbitMQConfig;
import com.officereservation.reservationservice.dataaccess.outbox.OutboxMessageRepository;
import com.officereservation.reservationservice.model.outbox.OutboxMessage;
import com.officereservation.reservationservice.model.outbox.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxRelayService {
    private final OutboxMessageRepository outboxMessageRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 5000)
    public void relay() {
        List<OutboxMessage> pendingMessages = outboxMessageRepository.findByStatus(OutboxStatus.PENDING);

        for (OutboxMessage message : pendingMessages) {
            try {
                Message amqpMessage = MessageBuilder
                        .withBody(message.getPayload().getBytes(StandardCharsets.UTF_8))
                        .andProperties(MessagePropertiesBuilder.newInstance()
                                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                                .setMessageId(message.getId().toString())
                                .setHeader("__TypeId__", message.getEventType())
                                .build())
                        .build();

                rabbitTemplate.send(RabbitMQConfig.RESERVATION_EXCHANGE, message.getRoutingKey(), amqpMessage);

                message.setStatus(OutboxStatus.PUBLISHED);
                message.setPublishedAt(LocalDateTime.now());
                outboxMessageRepository.save(message);

                log.info("Relayed outbox message id={}, routingKey={}", message.getId(), message.getRoutingKey());
            } catch (Exception e) {
                log.warn("Failed to relay outbox message id={}, will retry on next tick", message.getId(), e);
            }
        }
    }
}
