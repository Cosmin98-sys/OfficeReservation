package com.officereservation.notificationservice.model.deduplication;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_messages")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedMessage {

    @Id
    private String messageId;

    @Column(nullable = false)
    private LocalDateTime processedAt;
}