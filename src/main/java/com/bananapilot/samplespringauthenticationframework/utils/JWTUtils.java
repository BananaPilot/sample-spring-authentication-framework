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


public class JWTUtils {

    @Value("${framework.jwt.hashKey}")
    private String hashKey;

    @Value("${framework.jwt.advice}")
    private String audience;

    @Value("${framework.jwt.timeToExpire}")
    private long timeToExpire;

    private Key key;

    @PostConstruct
    public void generateKey() {
        key = Keys.hmacShaKeyFor(hashKey.getBytes());
    }

    public String getJWT(String username, int userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setAudience(audience)
                .setExpiration(new Date(System.currentTimeMillis() + timeToExpire))
                .setClaims(new HashMap<>() {{
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
