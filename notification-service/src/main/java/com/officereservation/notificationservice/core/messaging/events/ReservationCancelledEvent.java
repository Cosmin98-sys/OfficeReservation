package com.officereservation.notificationservice.core.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelledEvent implements Serializable {
    private Long reservationId;
    private Long userId;
    private String workstationName;
    private LocalDate date;
}
