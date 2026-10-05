package com.example.springboot_masterclass.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springboot_masterclass.dto.AuthRequestDTO;
import com.example.springboot_masterclass.entity.User;
import com.example.springboot_masterclass.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(AuthRequestDTO request) {

        User user = new User();

        user.setUsername(request.getUsername());

        // Hash the password before saving
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(request.getRole());

        userRepository.save(user);

        return "User registered successfully";
    }
}