/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
/**
 *
 * @author motho
 */

@Configuration
public class SecurityConfig 
{
    // Controls which endpoints users can access.
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // Disables CSRF for this local REST API example.
            .csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                // Anyone can check whether the application is running.
                .requestMatchers("/api/health").permitAll()

                // Only managers can delete jobs.
                .requestMatchers(HttpMethod.DELETE, "/api/jobs/**").hasRole("MANAGER")

                // Other job requests require login.
                .requestMatchers("/api/jobs/**").authenticated()

                // Blocks other URLs.
                .anyRequest().denyAll())

            // Uses a username and password to log in.
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Creates two accounts for testing.
    @Bean
    public UserDetailsService users(PasswordEncoder encoder) {

        UserDetails technician = User.builder()
                .username("technician")
                .password(encoder.encode("TechFix2026!"))
                .roles("TECHNICIAN")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password(encoder.encode("ManagerFix2026!"))
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(technician, manager);
    }

    // Encodes passwords before comparing them during login.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
