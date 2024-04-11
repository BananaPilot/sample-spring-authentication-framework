package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.AnnotationImpl;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.BasicAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.FloorLevelAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.NoAuthorization;
import com.bananapilot.samplespringauthenticationframework.service.UserDetailsService;
import com.bananapilot.samplespringauthenticationframework.types.UserDetails;
import com.bananapilot.samplespringauthenticationframework.utils.Constants;
import com.bananapilot.samplespringauthenticationframework.utils.FloorLevelImpl;
import com.bananapilot.samplespringauthenticationframework.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Order(2)
public class MethodFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserDetailsService userService;

    @Autowired(required = false)
    FloorLevelImpl floorLevel;

    @Autowired
    AnnotationImpl annotationImpl;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(Constants.HANDLER_METHOD);
        return handlerMethod.hasMethodAnnotation(NoAuthorization.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader(Constants.AUTHORIZATION_HEADER) == null) {
            response.sendError(401, "Unauthorized");
            filterChain.doFilter(request, response);
            return;
        }
        Jws<Claims> claimsJws = jwtUtils.validate(request.getHeader(Constants.AUTHORIZATION_HEADER));
        UserDetails userDetails = UserDetails.UserDetailsBuilder.anUserDetails()
                .withId(claimsJws.getBody().get("user-id", UUID.class))
                .withUsername(claimsJws.getBody().get("user-username", String.class))
                .withRoles(List.of(claimsJws.getBody().get("user-roles", String.class).split(", ")))
                .build();
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(Constants.HANDLER_METHOD);
        if (handlerMethod.hasMethodAnnotation(BasicAuthorization.class)) {
            annotationImpl.basicAuthorizationImpl(filterChain, response, request, userDetails, handlerMethod);
            return;
        }
        if (handlerMethod.hasMethodAnnotation(FloorLevelAuthorization.class)) {
            annotationImpl.floorLevelImpl(filterChain, response, request, userDetails, handlerMethod, floorLevel);
            return;
        }
        response.sendError(403, "Forbidden");
        filterChain.doFilter(request, response);
    }
}
