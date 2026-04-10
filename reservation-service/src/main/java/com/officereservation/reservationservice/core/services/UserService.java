package com.officereservation.reservationservice.core.services;

import com.officereservation.reservationservice.core.dtos.commands.user.UpdateUserRequest;
import com.officereservation.reservationservice.dataaccess.user.RoleRepository;
import com.officereservation.reservationservice.dataaccess.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void update(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var roles = request.getRoles().stream()
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role)))
                .collect(Collectors.toSet());

        user.setFullName(request.getFullName());
        user.setRoles(roles);

        userRepository.save(user);
    }
}