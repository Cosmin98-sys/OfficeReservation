package com.officereservation.reservationservice.api.controllers;

import com.officereservation.reservationservice.core.dtos.requests.workstation.CreateWorkstationRequest;
import com.officereservation.reservationservice.core.dtos.responses.workstation.GetAvailableWorkstationsResponse;
import com.officereservation.reservationservice.core.services.WorkstationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/workstations")
@RequiredArgsConstructor
public class WorkstationController {

    private final WorkstationService workstationService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<Long> create(@Valid @RequestBody CreateWorkstationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workstationService.create(request));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<GetAvailableWorkstationsResponse>> getAvailable(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(workstationService.getAvailableWorkstations(date, PageRequest.of(page, size)));
    }
}