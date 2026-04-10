package com.officereservation.reservationservice.dataaccess.workstation;

import com.officereservation.reservationservice.model.workstation.Workstation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    @Query("SELECT w FROM Workstation w WHERE w.isActive = true AND " +
            "(SELECT COUNT(r) FROM Reservation r WHERE r.workstation.id = w.id AND r.date = :date AND r.status = 'ACTIVE') < w.capacity")
    Page<Workstation> getAvailableForDate(@Param("date") LocalDate date, Pageable pageable);
}