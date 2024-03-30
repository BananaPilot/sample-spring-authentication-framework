package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.FloorLevelAuthorization;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class FloorLevelAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtToken;

    private final FloorLevelAuthorization annotation;

    public FloorLevelAuthenticationFilter(String jwtToken, FloorLevelAuthorization annotation) {
        this.jwtToken = jwtToken;
        this.annotation = annotation;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    }
}
