package com.officereservation.reservationservice.dataaccess.repository.user;

import com.officereservation.reservationservice.model.user.Role;
import com.officereservation.reservationservice.model.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}