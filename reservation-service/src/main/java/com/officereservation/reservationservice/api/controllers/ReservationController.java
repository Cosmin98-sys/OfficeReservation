package com.officereservation.reservationservice.api.controllers;

import com.officereservation.reservationservice.core.dtos.requests.reservation.CreateReservationRequest;
import com.officereservation.reservationservice.core.dtos.responses.reservation.GetAllReservationsByUserIdResponse;
import com.officereservation.reservationservice.core.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody CreateReservationRequest request, @AuthenticationPrincipal Jwt jwt){
        Long userId = ((Number) jwt.getClaim("userId")).longValue();
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(userId,request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<GetAllReservationsByUserIdResponse>> getMyReservations(@AuthenticationPrincipal Jwt jwt) {
        Long userId = ((Number) jwt.getClaim("userId")).longValue();
        return ResponseEntity.ok(reservationService.getAllReservationsByUserId(userId));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt){
        Long userId = ((Number) jwt.getClaim("userId")).longValue();
        reservationService.cancel(id,userId);
        return ResponseEntity.noContent().build();
    }
}
