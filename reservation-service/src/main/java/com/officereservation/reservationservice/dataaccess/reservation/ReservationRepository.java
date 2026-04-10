package com.officereservation.reservationservice.dataaccess.reservation;

import com.officereservation.reservationservice.model.reservation.Reservation;
import com.officereservation.reservationservice.model.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByUserIdAndDateAndStatus(Long userId, LocalDate date, ReservationStatus status);

    long countByWorkstationIdAndDateAndStatus(Long workstationId, LocalDate date, ReservationStatus status);
}
