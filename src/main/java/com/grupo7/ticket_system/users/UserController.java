package com.grupo7.ticket_system.users;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.ticket_system.LoginSecurity.JwtService;
import com.grupo7.ticket_system.models.User;

@RestController
@RequestMapping("/users")
public class UserController{

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    record LoginRequest(String username, String password) {}

    UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registerUser")
    public User registerUser(@RequestBody User user) { 
        return userService.registerUser(user);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        String token= jwtService.generateToken(loginRequest.username());
        String role = userService.getRoleByUsername(loginRequest.username());
        return ResponseEntity.ok(Map.of("token", token, "role", role));
    }

    @GetMapping("/postal-codes")
    public List<String> getPostalCodes() {
        return userService.getPostalCodes();
    }

    @GetMapping("/me/tickets")
    public List<Map<String, Object>> getMyTickets() {
        return userService.getMyTickets();
    }

    @GetMapping("/me/sales")
    public List<Map<String, Object>> getMySales() {
        return userService.getMySales();
    }

    @GetMapping("/me/transfers")
    public List<Map<String, Object>> getMyTransfers() {
        return userService.getMyTransfers();
    }

    @GetMapping("/rankings/top-buyers")
    public List<Map<String, Object>> getTopBuyers() {
        return userService.getTopBuyers();
    }
}
