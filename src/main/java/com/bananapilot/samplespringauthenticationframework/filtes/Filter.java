package com.bananapilot.samplespringauthenticationframework.filtes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class Filter {
    Jws<Claims> claimsJws;

    public Filter(Jws<Claims> claimsJws) {
        this.claimsJws = claimsJws;
    }

    public Filter() {

    }

    public Jws<Claims> getClaimsJws() {
        return claimsJws;
    }

    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;
}
