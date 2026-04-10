package com.officereservation.reservationservice.core.services;

import com.officereservation.reservationservice.core.dtos.commands.workstation.CreateWorkstationRequest;
import com.officereservation.reservationservice.dataaccess.workstation.WorkstationRepository;
import com.officereservation.reservationservice.model.workstation.Workstation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkstationService {

    private final WorkstationRepository workstationRepository;

    public Long create(CreateWorkstationRequest request) {
        Workstation workstation = Workstation.builder()
                .name(request.getName())
                .floor(request.getFloor())
                .zone(request.getZone())
                .type(request.getType())
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .build();

        return workstationRepository.save(workstation).getId();
    }
}