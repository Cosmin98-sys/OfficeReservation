package com.officereservation.notificationservice.core.dtos.responses;

import com.officereservation.notificationservice.model.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
@AllArgsConstructor
public class GetAllNotificationsByUserIdResponse {
    private Long id;
    private String message;
    private NotificationType type;
    private boolean isRead;
    private String createdAt;

    private static final DateTimeFormatter ROMANIAN_FORMAT =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(new Locale("ro", "RO"));

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(ROMANIAN_FORMAT);
    }
}