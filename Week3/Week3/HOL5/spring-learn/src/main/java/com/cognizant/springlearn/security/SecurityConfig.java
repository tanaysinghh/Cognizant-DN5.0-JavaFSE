package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * HOL 5: Spring Security config.
 *
 * IMPORTANT: The hands-on doc references `WebSecurityConfigurerAdapter`, which was REMOVED
 * in Spring Security 6 (used by Spring Boot 3). The modern equivalent is exposing a
 * `SecurityFilterChain` bean. Functionally identical; this is the only style that compiles
 * on Boot 3.
 *
 * Users (in-memory):
 *   admin / pwd (ROLE_ADMIN)
 *   user  / pwd (ROLE_USER)
 *
 * Rules:
 *   /hello           → public (kept open for demo)
 *   /authenticate    → HTTP Basic (USER or ADMIN) — returns a fresh JWT
 *   everything else  → JWT required (Bearer token)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(encoder.encode("pwd"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("user")
                .password(encoder.encode("pwd"))
                .roles("USER")
                .build());
        return manager;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager)
            throws Exception {
        LOGGER.info("Start securityFilterChain()");

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/hello").permitAll()
                    .requestMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
                    .anyRequest().authenticated()
            )
            .httpBasic(basic -> {})
            .addFilterBefore(new JwtAuthorizationFilter(authManager),
                    BasicAuthenticationFilter.class);

        return http.build();
    }
}
