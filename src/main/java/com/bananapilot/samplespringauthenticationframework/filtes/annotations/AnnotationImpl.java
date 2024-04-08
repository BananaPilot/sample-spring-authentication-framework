package com.bananapilot.samplespringauthenticationframework.filtes.annotations;

import com.bananapilot.samplespringauthenticationframework.types.UserDetails;
import com.bananapilot.samplespringauthenticationframework.utils.Constants;
import com.bananapilot.samplespringauthenticationframework.utils.FloorLevelImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnnotationImpl {
    public void basicAuthorizationImpl(FilterChain filterChain, HttpServletResponse response, HttpServletRequest request, UserDetails userDetails, HandlerMethod handlerMethod) throws ServletException, IOException {
        BasicAuthorization annotation = handlerMethod.getMethodAnnotation(BasicAuthorization.class);
        List<String> roles = Arrays.asList(annotation.roles());
        for (String role : roles) {
            if (userDetails.getRoles().contains(role)) {
                response.setStatus(200);
                response.setHeader(Constants.AUTHORIZATION_HEADER, Constants.AUTHENTICATED);
                filterChain.doFilter(request, response);
            }
        }
    }

    public void floorLevelImpl(FilterChain filterChain, HttpServletResponse response, HttpServletRequest request, UserDetails userDetails, HandlerMethod handlerMethod, FloorLevelImpl floorLevel) throws ServletException, IOException {
        String role = handlerMethod.getMethodAnnotation(FloorLevelAuthorization.class).floorRole();
        if (floorLevel.isRoleGreaterOrEquals(role, userDetails.getRoles())) {
            response.setStatus(200);
            response.setHeader(Constants.AUTHORIZATION_HEADER, Constants.AUTHENTICATED);
        } else response.sendError(403, "Forbidden");
        filterChain.doFilter(request, response);
    }
}
