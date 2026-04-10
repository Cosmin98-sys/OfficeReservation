package com.officereservation.notificationservice.api.controllers;

import com.officereservation.notificationservice.core.dtos.responses.GetAllNotificationsByUserIdResponse;
import com.officereservation.notificationservice.core.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my")
    public ResponseEntity<Page<GetAllNotificationsByUserIdResponse>> getMyNotifications(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long userId = ((Number) jwt.getClaim("userId")).longValue();
        return ResponseEntity.ok(notificationService.getAllNotificationsByUserId(
                userId, PageRequest.of(page, size, Sort.by("createdAt").descending())));
    }
}