package com.officereservation.reservationservice.model.workstation;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workstations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workstation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private String zone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkstationType type;

    @Column(nullable = false)
    @Builder.Default
    private int capacity = 1;

    private String description;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean isActive = true;
}
