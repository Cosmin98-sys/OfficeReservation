package com.officereservation.reservationservice.core.services;

import com.officereservation.reservationservice.core.dtos.responses.user.AuthResponse;
import com.officereservation.reservationservice.core.dtos.commands.user.LoginRequest;
import com.officereservation.reservationservice.core.dtos.commands.user.RegisterRequest;
import com.officereservation.reservationservice.model.user.RoleName;
import com.officereservation.reservationservice.model.user.User;
import com.officereservation.reservationservice.dataaccess.user.RoleRepository;
import com.officereservation.reservationservice.dataaccess.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        var userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("Role ROLE_USER not found"));

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .roles(new HashSet<>(Set.of(userRole)))
                .build();

        userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(user), user.getEmail(), getRoleNames(user));
    }

    public AuthResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = (User) authentication.getPrincipal();

        return new AuthResponse(jwtService.generateToken(user), user.getEmail(), getRoleNames(user));
    }

    private List<String> getRoleNames(User user) {
        return user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }
}