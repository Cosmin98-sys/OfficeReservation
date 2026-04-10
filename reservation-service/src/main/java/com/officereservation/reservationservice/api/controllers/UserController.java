package com.officereservation.reservationservice.api.controllers;

import com.officereservation.reservationservice.core.dtos.requests.user.UpdateUserRequest;
import com.officereservation.reservationservice.core.dtos.responses.user.GetUserByIdResponse;
import com.officereservation.reservationservice.core.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        userService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetUserByIdResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}