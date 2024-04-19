package com.bananapilot.samplespringauthenticationframework.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class JWTUtils {
    //to declare in to application.properties
    @Value("${framework.jwt.hashKey}")
    private String hashKey;
    //to declare in to application.properties
    @Value("${framework.jwt.advice}")
    private String audience;
    //to declare in to application.properties
    @Value("${framework.jwt.timeToExpire}")
    private long timeToExpire;
    private Key key;

    //after the creation on the instance parses a key as a signature
    @PostConstruct
    public void generateKey() {
        key = Keys.hmacShaKeyFor(hashKey.getBytes());
    }

    public String getJWT(String username, Long userId, String roles) {
        return Jwts.builder()
                .setSubject(username)
                .setAudience(audience)
                .setExpiration(new Date(System.currentTimeMillis() + timeToExpire))
                .setClaims(new HashMap<>() {{
                    put("user-username", username);
                    put("user-id", userId);
                    put("user-roles", roles);
                }})
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validate(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);
    }
}
