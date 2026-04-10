package com.officereservation.reservationservice.api.controllers;

import com.officereservation.reservationservice.core.dtos.commands.workstation.CreateWorkstationRequest;
import com.officereservation.reservationservice.core.services.WorkstationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workstations")
@RequiredArgsConstructor
public class WorkstationController {

    private final WorkstationService workstationService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<Long> create(@Valid @RequestBody CreateWorkstationRequest request) {
        return ResponseEntity.ok(workstationService.create(request));
    }
}