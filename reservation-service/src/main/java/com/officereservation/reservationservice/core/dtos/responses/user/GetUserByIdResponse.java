package com.officereservation.reservationservice.core.dtos.responses.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetUserByIdResponse {
    private Long id;
    private String email;
    private String fullName;
    private List<String> roles;
}
