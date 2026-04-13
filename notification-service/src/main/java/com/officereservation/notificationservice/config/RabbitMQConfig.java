package com.officereservation.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RESERVATION_EXCHANGE = "reservation.exchange";
    public static final String RESERVATION_CREATED_QUEUE = "notification.reservation.created";
    public static final String RESERVATION_CREATED_ROUTING_KEY = "reservation.created";
    public static final String RESERVATION_CANCELLED_QUEUE = "notification.reservation.cancelled";
    public static final String RESERVATION_CANCELLED_ROUTING_KEY = "reservation.cancelled";

    @Bean
    public TopicExchange reservationExchange() {
        return new TopicExchange(RESERVATION_EXCHANGE);
    }

    @Bean
    public Queue reservationCreatedQueue() {
        return QueueBuilder.durable(RESERVATION_CREATED_QUEUE).build();
    }

    @Bean
    public Binding reservationCreatedBinding(Queue reservationCreatedQueue, TopicExchange reservationExchange) {
        return BindingBuilder
                .bind(reservationCreatedQueue)
                .to(reservationExchange)
                .with(RESERVATION_CREATED_ROUTING_KEY);
    }

    @Bean
    public Queue reservationCancelledQueue() {
        return QueueBuilder.durable(RESERVATION_CANCELLED_QUEUE).build();
    }

    @Bean
    public Binding reservationCancelledBinding(Queue reservationCancelledQueue, TopicExchange reservationExchange) {
        return BindingBuilder
                .bind(reservationCancelledQueue)
                .to(reservationExchange)
                .with(RESERVATION_CANCELLED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
