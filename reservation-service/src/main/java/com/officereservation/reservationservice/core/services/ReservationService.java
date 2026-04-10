package com.officereservation.reservationservice.core.services;

import com.officereservation.reservationservice.core.dtos.requests.reservation.CreateReservationRequest;
import com.officereservation.reservationservice.core.dtos.responses.reservation.GetAllReservationsByUserIdResponse;
import com.officereservation.reservationservice.dataaccess.reservation.ReservationRepository;
import com.officereservation.reservationservice.dataaccess.user.UserRepository;
import com.officereservation.reservationservice.dataaccess.workstation.WorkstationRepository;
import com.officereservation.reservationservice.model.reservation.Reservation;
import com.officereservation.reservationservice.model.reservation.ReservationStatus;
import com.officereservation.reservationservice.model.user.User;
import com.officereservation.reservationservice.model.workstation.Workstation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final WorkstationRepository workstationRepository;
    private final UserRepository userRepository;

    public Long create(Long userId, CreateReservationRequest request) {

        if (reservationRepository.existsByUserIdAndDateAndStatus(userId, request.getDate(), ReservationStatus.ACTIVE)) {
            throw new IllegalStateException("You already have an active reservation for this day");
        }

        long activeCount = reservationRepository.countByWorkstationIdAndDateAndStatus(
                request.getWorkstationId(), request.getDate(), ReservationStatus.ACTIVE
        );

        Workstation workstation = workstationRepository.findById(request.getWorkstationId())
                .orElseThrow(() -> new IllegalArgumentException("Workstation not found"));

        if (activeCount >= workstation.getCapacity()) {
            throw new IllegalStateException("This workstation is fully booked for this day");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Reservation reservation = Reservation.builder()
                .user(user)
                .workstation(workstation)
                .date(request.getDate())
                .build();

        return reservationRepository.save(reservation).getId();
    }

    public List<GetAllReservationsByUserIdResponse> getAllReservationsByUserId(Long userId) {
        return reservationRepository.getAllByUserId(userId).stream()
                .map(r -> new GetAllReservationsByUserIdResponse(r.getId(), r.getWorkstation().getName(),
                        GetAllReservationsByUserIdResponse.formatDate(r.getDate()), r.getStatus(), r.getCreatedAt()))
                .toList();
    }

    public void cancel(Long reservationId, Long userId){
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId,userId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if(reservation.getStatus() == ReservationStatus.CANCELLED){
            throw new IllegalStateException("Reservation is already cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
}
