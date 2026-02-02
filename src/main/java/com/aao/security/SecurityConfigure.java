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
            .cors(cors -> {})

            .authorizeHttpRequests(auth -> auth

               
                .requestMatchers("/api/auth/**").permitAll()

               
                .requestMatchers(
                    "/api/v1/properties/getAll",
                    "/api/v1/properties/search",
                    "/api/v1/properties/*/images",
                    "/api/v1/properties/*" , 
                    "/api/v1/bookings/book",
                    "/api/v1/bookings/properties/availability/**"
                ).permitAll()

                
                .requestMatchers("/api/v1/properties").hasAnyRole("ADMIN", "HOST")      
                .requestMatchers("/api/v1/properties/*").hasAnyRole("ADMIN", "HOST")    

                
                .requestMatchers("/api/v1/admins/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/upload/image").hasAnyRole("ADMIN", "HOST")
                .requestMatchers("/api/v1/bookings/getBooking/**").hasAnyRole("ADMIN","HOST")
                .requestMatchers("/api/v1/bookings/reference/**").authenticated()
                .requestMatchers("/api/v1/bookings/getAllbookingsOfUser").hasAnyRole("ADMIN","HOST")
                .requestMatchers("/api/v1/bookings/cancel/**") .authenticated()
//                .requestMatchers("/api/v1/hosts/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/hosts/getAll").hasRole("ADMIN")
                .requestMatchers("/api/v1/hosts/delete/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/hosts/activate/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/hosts/deactivate/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/hosts/completeProfile").hasRole("HOST")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )

            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
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
