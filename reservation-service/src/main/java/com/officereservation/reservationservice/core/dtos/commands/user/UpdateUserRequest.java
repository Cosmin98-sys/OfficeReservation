package com.officereservation.reservationservice.core.dtos.commands.user;

import com.officereservation.reservationservice.model.user.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotEmpty(message = "At least one role is required")
    private Set<RoleName> roles;
}
