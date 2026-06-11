package com.grupo7.ticket_system.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class Config {
    
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
