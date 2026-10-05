package com.example.springboot_masterclass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TEMPORARY IN-MEMORY USERS
    @Bean
    public UserDetailsManager users(PasswordEncoder passwordEncoder) {

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // SECURITY CONFIGURATION
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            // Disable CSRF for REST API practice
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Registration does not require authentication
                .requestMatchers("/api/auth/register").permitAll()

                // USER + ADMIN can GET employees
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/employees/**"
                ).hasAnyRole("USER", "ADMIN")

                // ADMIN only
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/employees"
                ).hasRole("ADMIN")

                // ADMIN only
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                // ADMIN only
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // Basic Authentication for now
            .httpBasic(customizer -> {});

        return http.build();
    }
}