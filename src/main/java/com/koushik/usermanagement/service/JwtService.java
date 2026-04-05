package com.koushik.usermanagement.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    @Value("${jwt.secret}")
    private String secret ;
    @Value("${jwt.expiry}")
    private long expiry;

    private Key getSignKey(){

        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String email,String role){
        Map<String, Object> claim = new HashMap<>();
        claim.put("role",role);
        String token = Jwts.builder()
                .setSubject(email)
                .addClaims(claim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiry))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        log.info("JWT Token Created");
        return token;
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role",String.class);
    }

    public boolean isTokenValid(String token) {
        try{
           Jwts.parserBuilder()
                   .setSigningKey(getSignKey())
                   .build()
                   .parseClaimsJws(token);
           log.info("Token Validated");
           return true;
        }catch(Exception e){
            log.error("Token Is Invalid");
            return false;
        }
    }
}
