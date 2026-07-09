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
 * - If the Authorization: Bearer <token> header is valid → sets the security context.
 * - If missing → falls through to next filter (Basic auth handles /authenticate).
 * - If present but invalid → cleared context, will result in 401 later.
 *
 * NOTE: The original hands-on used the old javax.servlet + old jjwt API.
 * Migrated to jakarta.servlet + jjwt 0.12 for Spring Boot 3.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Start doFilterInternal()");

        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            // No token → let downstream filters (e.g. Basic auth) handle it
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
        LOGGER.info("End doFilterInternal()");
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
