package com.officereservation.notificationservice.dataaccess.deduplication;

import com.officereservation.notificationservice.model.deduplication.ProcessedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, String> {
}