package com.aao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aao.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC ROUTES
                        .requestMatchers("/api/bookings/guest").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // .requestMatchers("/api/v1/rooms/property/**").permitAll()
                        .requestMatchers(
                                "/api/v1/properties/getAll",
                                "/api/v1/properties/search",
                                "/api/v1/properties/*/images",
                                "/api/v1/properties/*",
                                "/api/properties/*/images",
                                "/api/v1/rooms/property/**",
                                "/api/v1/rooms/*/book",
                                "/api/v1/bookings/calculate-price",
                                "/api/v1/bookings/properties/*/availability")
                        .permitAll()
                        
                        .requestMatchers("/api/v1/properties").hasAnyRole("ADMIN", "HOST")
                        .requestMatchers("/api/v1/properties/*").hasAnyRole("ADMIN", "HOST")


                    // .requestMatchers("/api/v1/bookings/**").hasAnyAuthority("USER", "GUEST", "ADMIN", "HOST")

                        
                    // .requestMatchers("/api/v1/booking-guests/**").hasAnyAuthority("USER", "GUEST", "ADMIN", "HOST")

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/admins/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/upload/image").hasAnyRole("ADMIN", "HOST")
                        .requestMatchers("/api/v1/properties").hasAnyRole("ADMIN", "HOST")
                        .anyRequest().authenticated())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
