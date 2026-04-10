package com.officereservation.reservationservice.core.dtos.responses.workstation;

import com.officereservation.reservationservice.model.workstation.WorkstationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAvailableWorkstationsResponse {
    private Long id;
    private String name;
    private int floor;
    private String zone;
    private WorkstationType type;
    private int capacity;
    private String description;
}