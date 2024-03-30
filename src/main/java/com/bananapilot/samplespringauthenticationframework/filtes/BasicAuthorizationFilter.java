package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.BasicAuthorization;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BasicAuthorizationFilter extends OncePerRequestFilter {

    private final Jws<Claims> jwtToken;

    private final BasicAuthorization annotation;


    public BasicAuthorizationFilter(Jws<Claims> jwtToken, BasicAuthorization annotation) {
        this.jwtToken = jwtToken;
        this.annotation = annotation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
