package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;

@Component
@Order(0)
public class FilterChanInit extends Filter {

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Filter filter = FilterFactory.doFactory(jwtUtils.validate(request.getHeader("Authorization")), (HandlerMethod) request.getAttribute("handleMethodForAuthorization"));
        Assert.notNull(filter, "filter is null something went wrong");
        filter.doFilter(request, response, filterChain);
    }
}
