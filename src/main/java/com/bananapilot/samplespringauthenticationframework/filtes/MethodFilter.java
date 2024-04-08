package com.bananapilot.samplespringauthenticationframework.filtes;

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
import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class MethodFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserDetailsService userService;

    @Autowired(required = false)
    FloorLevelImpl floorLevel;

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
        Jws<Claims> claimsJws = jwtUtils.validate(request.getHeader("Authorization"));
        UserDetails userDetails = UserDetails.UserDetailsBuilder.anUserDetails()
                .withId(claimsJws.getBody().get("userDetails-id", Integer.class))
                .withUsername(claimsJws.getBody().get("userDetails-username", String.class))
                .withRoles(List.of(claimsJws.getBody().get("userDetails-roles", String.class).split(", ")))
                .build();
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(Constants.HANDLER_METHOD);
        if (handlerMethod.hasMethodAnnotation(BasicAuthorization.class)) {
            BasicAuthorization annotation = handlerMethod.getMethodAnnotation(BasicAuthorization.class);
            List<String> roles = Arrays.asList(annotation.roles());
            for (String role : roles) {
                if (userDetails.getRoles().contains(role)) {
                    response.setStatus(200);
                    response.setHeader(Constants.AUTHORIZATION_HEADER, Constants.AUTHENTICATED);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        if (handlerMethod.hasMethodAnnotation(FloorLevelAuthorization.class)) {
            String role = handlerMethod.getMethodAnnotation(FloorLevelAuthorization.class).floorRole();
            if (floorLevel.isRoleGreaterOrEquals(role, userDetails.getRoles())) {
                response.setStatus(200);
                response.setHeader(Constants.AUTHORIZATION_HEADER, Constants.AUTHENTICATED);
            } else response.sendError(403, "Forbidden");
            filterChain.doFilter(request, response);
            return;
        }
        response.sendError(403, "Forbidden");
        filterChain.doFilter(request, response);
    }
}
