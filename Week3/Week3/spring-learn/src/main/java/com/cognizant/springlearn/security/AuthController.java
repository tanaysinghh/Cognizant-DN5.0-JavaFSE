package com.cognizant.springlearn.security;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * HOL 5: /authenticate issues a JWT after successful HTTP Basic authentication.
 * curl -s -u user:pwd http://localhost:8083/authenticate
 */
@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private static final long EXPIRY_MS = 60 * 60 * 1000L; // 1 hour

    @GetMapping("/authenticate")
    public String authenticate() {
        LOGGER.info("Start authenticate()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        SecretKey key = JwtKeyProvider.getKey();
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRY_MS))
                .signWith(key)
                .compact();

        LOGGER.info("Issued token for {}", username);
        return token;
    }
}
