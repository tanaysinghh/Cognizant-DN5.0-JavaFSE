package com.cognizant.springlearn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * HOL 5: JWT authorization filter.
 *  - If Authorization: Bearer <token> is present + valid → sets the security context.
 *  - If missing → passes through (Basic auth will handle /authenticate).
 *  - If present but invalid → context stays empty, request fails as 401.
 *
 * Migration note: original doc uses javax.servlet + old jjwt API.
 * On Spring Boot 3 → jakarta.servlet + jjwt 0.12 (verifyWith/parseSignedClaims).
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;

        String token = header.replace("Bearer ", "").trim();
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(JwtKeyProvider.getKey())
                    .build()
                    .parseSignedClaims(token);

            String user = jws.getPayload().getSubject();
            LOGGER.debug("Authenticated as {}", user);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        } catch (JwtException ex) {
            LOGGER.warn("Invalid JWT: {}", ex.getMessage());
        }
        return null;
    }
}
