package com.example.springboot_masterclass;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashTest {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = "jaya123";

        // Hash the password
        String hashedPassword = encoder.encode(password);

        System.out.println("Original Password:");
        System.out.println(password);

        System.out.println();

        System.out.println("Hashed Password:");
        System.out.println(hashedPassword);

        System.out.println();

        // Verify password
        boolean matches =
                encoder.matches(password, hashedPassword);

        System.out.println("Password matches:");
        System.out.println(matches);
    }
}