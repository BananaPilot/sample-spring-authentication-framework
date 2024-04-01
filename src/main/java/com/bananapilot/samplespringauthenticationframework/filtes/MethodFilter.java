package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.BasicAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.FloorLevelAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.NoAuthorization;
import com.bananapilot.samplespringauthenticationframework.service.UserService;
import com.bananapilot.samplespringauthenticationframework.types.User;
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
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class MethodFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    FloorLevelImpl floorLevel;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getHeader("Authorization").isBlank();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
        Jws<Claims> claimsJws = jwtUtils.validate(request.getHeader("Authorization"));
        if (jwtUtils == null) {
            response.setStatus(401);
            Assert.notNull(claimsJws, "jws claims are null");
        }
        User user = User.UserBuilder.anUser()
                .withId(claimsJws.getBody().get("user-id", Integer.class))
                .withUsername(claimsJws.getBody().getSubject())
                .withRoles(claimsJws.getBody().get("user-roles", List.class))
                .build();
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("handleMethodForAuthorization");
        if (handlerMethod.hasMethodAnnotation(BasicAuthorization.class)) {
            BasicAuthorization annotation = handlerMethod.getMethodAnnotation(BasicAuthorization.class);
            List<String> roles = Arrays.asList(annotation.roles());
            for (String role : roles) {
                if (user.getRoles().contains(role)) {
                    response.setStatus(200);
                    response.addHeader("Authorization", "authenticated");
                }
            }
            filterChain.doFilter(request, response);
        }
        if (handlerMethod.hasMethodAnnotation(FloorLevelAuthorization.class)) {
            String role = handlerMethod.getMethodAnnotation(FloorLevelAuthorization.class).floorRole();
            if (floorLevel.isRoleGreaterOrEquals(role, user.getRoles())) {
                response.setStatus(200);
                response.addHeader("Authorization", "authenticated");
            }
            filterChain.doFilter(request, response);
        }
        if (handlerMethod.hasMethodAnnotation(NoAuthorization.class)) {
            filterChain.doFilter(request, response);
        }
        response.setStatus(403);
    }
}
