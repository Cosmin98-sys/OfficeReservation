package com.officereservation.reservationservice.core.services;

import com.officereservation.reservationservice.core.dtos.requests.workstation.CreateWorkstationRequest;
import com.officereservation.reservationservice.core.dtos.responses.workstation.GetAvailableWorkstationsResponse;
import com.officereservation.reservationservice.dataaccess.workstation.WorkstationRepository;
import com.officereservation.reservationservice.model.workstation.Workstation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public Page<GetAvailableWorkstationsResponse> getAvailableWorkstations(LocalDate date, Pageable pageable) {
        return workstationRepository.getAvailableForDate(date, pageable)
                .map(w -> new GetAvailableWorkstationsResponse(w.getId(), w.getName(), w.getFloor(),
                        w.getZone(), w.getType(), w.getCapacity(), w.getDescription()));
    }
}