package com.grupo7.ticket_system.LoginSecurity;
import com.grupo7.ticket_system.LoginSecurity.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.grupo7.ticket_system.users.UserService;

@Configuration
@EnableWebSecurity
public class Config {
    
    private final JwtFilter jwtFilter;
    private final UserService userService;


    Config(JwtFilter jwtFilter, UserService userService) {
        this.jwtFilter = jwtFilter;
        this.userService= userService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Frontend estatico
                .requestMatchers("/", "/index.html", "/styles.css", "/app.js", "/favicon.ico", "/error").permitAll()

                // Login y registro publicos
                .requestMatchers("/users/loginUser", "/users/registerUser", "/users/postal-codes").permitAll() //

                // Consultas/rankings agregadas para la demo
                .requestMatchers("/users/rankings/**").hasRole("ADMIN")
                .requestMatchers("/events/rankings/**", "/events/list").authenticated()

                // Operaciones segun rol
                .requestMatchers("/events/**", "/infrastructures/**").hasRole("ADMIN") //
                .requestMatchers("/sales/**", "/transfers/**").hasRole("USER") //
                .requestMatchers("/validates/**").hasRole("FUNCIONARIO") //

                // El resto requiere estar logueado
                .anyRequest().authenticated() //
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //

        return http.build(); //
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
