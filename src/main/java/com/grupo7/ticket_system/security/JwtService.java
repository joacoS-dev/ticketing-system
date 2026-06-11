package com.grupo7.ticket_system.security;

import java.util.Date;

import javax.crypto.SecretKey;

import com.grupo7.ticket_system.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtService {
    
    private final String SECRET= "esto-es-un-secreto-entre-nostros256"; //put this in application.properties

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 360000)).
        signWith(getKey()).compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    private boolean isExpired(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date()); 
    }

    public boolean isValid(String token, User user){
        String username= extractUsername(token);
        return username.equals(user.getUsername()) && !isExpired(token);
    }
}
