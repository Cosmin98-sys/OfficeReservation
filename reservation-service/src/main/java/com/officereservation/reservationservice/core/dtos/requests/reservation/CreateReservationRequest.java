package com.officereservation.reservationservice.core.dtos.requests.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateReservationRequest {
    @NotNull
    private Long workstationId;

    @NotNull
    @Future
    private LocalDate date;

}
