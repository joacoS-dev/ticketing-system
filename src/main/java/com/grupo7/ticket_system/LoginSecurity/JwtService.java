package com.grupo7.ticket_system.LoginSecurity;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.grupo7.ticket_system.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    
    @Value("${jwt.secret}")
    private String SECRET;   

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 3600000)).
        signWith(getKey()).compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    private boolean isExpired(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date()); 
    }

    public boolean isValid(String token, UserDetails userDetails){
        String username= extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }
}
