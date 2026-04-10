package com.officereservation.reservationservice.dataaccess.workstation;

import com.officereservation.reservationservice.model.workstation.Workstation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
}