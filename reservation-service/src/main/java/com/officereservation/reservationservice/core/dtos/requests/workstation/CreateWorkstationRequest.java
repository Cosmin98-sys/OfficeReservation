package com.officereservation.reservationservice.core.dtos.requests.workstation;

import com.officereservation.reservationservice.model.workstation.WorkstationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWorkstationRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Floor is required")
    private int floor;

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotNull(message = "Type is required")
    private WorkstationType type;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity = 1;

    private String description;
}