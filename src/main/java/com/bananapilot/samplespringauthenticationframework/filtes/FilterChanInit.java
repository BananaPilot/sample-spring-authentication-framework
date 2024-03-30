package com.bananapilot.samplespringauthenticationframework.filtes;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;

public class FilterChanInit extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        OncePerRequestFilter oncePerRequestFilter = FilterFactory.doFactory(request.getHeader("Authorization"), (HandlerMethod) request.getAttribute("handleMethodForAuthorization"));
        Assert.notNull(oncePerRequestFilter, "filter is null something went wrong");
        oncePerRequestFilter.doFilter(request, response, filterChain);
    }
}
