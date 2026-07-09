package com.cognizant.springlearn.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * HOL 5: Shared HMAC key for signing and verifying JWTs.
 *
 * NOTE: Hardcoded here for the hands-on. In real apps, load from an env var / secret manager.
 * jjwt 0.12 requires HS256 keys to be at least 256 bits (32 bytes) — hence the padding string.
 */
public final class JwtKeyProvider {

    private static final String SECRET = "spring-learn-week3-jwt-secret-key-do-not-use-in-prod-2026";
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private JwtKeyProvider() {}

    public static SecretKey getKey() { return KEY; }
}
