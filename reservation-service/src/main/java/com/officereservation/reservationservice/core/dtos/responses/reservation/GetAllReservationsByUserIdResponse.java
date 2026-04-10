package com.officereservation.reservationservice.core.dtos.responses.reservation;

import com.officereservation.reservationservice.model.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
@AllArgsConstructor
public class GetAllReservationsByUserIdResponse {
    private Long id;
    private String workstationName;
    private String date;
    private ReservationStatus status;
    private LocalDateTime createdAt;

    private static final DateTimeFormatter ROMANIAN_FORMAT =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(new Locale("ro", "RO"));

    public static String formatDate(LocalDate date) {
        return date.format(ROMANIAN_FORMAT);
    }
}
