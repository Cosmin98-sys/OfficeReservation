package com.officereservation.reservationservice.dataaccess.outbox;

import com.officereservation.reservationservice.model.outbox.OutboxMessage;
import com.officereservation.reservationservice.model.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage,Long> {
    List<OutboxMessage> findByStatus(OutboxStatus status);
}
