package com.example.springboot_masterclass.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.springboot_masterclass.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey secretKey;

    // JWT validity: 1 hour
    private final long expirationTime = 60 * 60 * 1000;

    public JwtService(
            @Value("${jwt.secret}") String secret) {

        this.secretKey =
                Keys.hmacShaKeyFor(
                        Decoders.BASE64.decode(secret)
                );
    }

    // GENERATE JWT
    public String generateToken(User user) {

        Date issuedAt = new Date();

        Date expiration =
                new Date(
                        issuedAt.getTime() + expirationTime
                );

        return Jwts.builder()

                // Subject = username
                .subject(user.getUsername())

                // Role
                .claim("role", user.getRole())

                // Created time
                .issuedAt(issuedAt)

                // Expiration time
                .expiration(expiration)

                // Sign JWT
                .signWith(secretKey)

                .compact();
    }

    // EXTRACT ALL CLAIMS
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // EXTRACT USERNAME
    public String extractUsername(String token) {

        return extractClaims(token)
                .getSubject();
    }

    // EXTRACT ROLE
    public String extractRole(String token) {

        return extractClaims(token)
                .get("role", String.class);
    }

    // CHECK EXPIRATION
    public boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // VALIDATE JWT
    public boolean isTokenValid(String token) {

        try {

            extractClaims(token);

            return !isTokenExpired(token);

        } catch (Exception exception) {

            return false;
        }
    }
}