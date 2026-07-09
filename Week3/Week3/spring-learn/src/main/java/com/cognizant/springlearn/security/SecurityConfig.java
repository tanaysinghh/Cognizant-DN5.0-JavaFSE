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
 * NOTE: The hands-on doc uses WebSecurityConfigurerAdapter, which was removed in
 * Spring Security 6 (Spring Boot 3). Replaced with the modern SecurityFilterChain
 * bean approach — functionally identical, and the only style that compiles on Boot 3.
 *
 * Users:
 *   admin / pwd (ROLE_ADMIN)
 *   user  / pwd (ROLE_USER)
 *
 * Rules:
 *   /authenticate → any authenticated user (Basic auth) — used to obtain a JWT
 *   everything else → JWT required
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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        LOGGER.info("Start securityFilterChain()");

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // HOL 2 endpoint is left open for demo
                    .requestMatchers("/hello").permitAll()
                    // /authenticate must be called with Basic auth to obtain a JWT
                    .requestMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
                    // everything else must have a valid JWT
                    .anyRequest().authenticated()
            )
            .httpBasic(basic -> {})
            // JWT filter runs before the default Basic auth filter
            .addFilterBefore(new JwtAuthorizationFilter(authManager),
                    BasicAuthenticationFilter.class);

        return http.build();
    }
}
