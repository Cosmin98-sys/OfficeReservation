package com.officereservation.notificationservice.core.services;

import com.officereservation.notificationservice.dataaccess.deduplication.ProcessedMessageRepository;
import com.officereservation.notificationservice.model.deduplication.ProcessedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeduplicationService {

    private final ProcessedMessageRepository processedMessageRepository;

    public boolean isDuplicate(String messageId) {
        if (messageId == null) return false;
        try {
            processedMessageRepository.save(new ProcessedMessage(messageId, LocalDateTime.now()));
            return false;
        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate message ignored: {}", messageId);
            return true;
        }
    }
}