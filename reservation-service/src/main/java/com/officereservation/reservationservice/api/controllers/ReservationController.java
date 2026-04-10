package com.officereservation.reservationservice.api.controllers;

import com.officereservation.reservationservice.core.dtos.commands.reservation.CreateReservationRequest;
import com.officereservation.reservationservice.core.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
