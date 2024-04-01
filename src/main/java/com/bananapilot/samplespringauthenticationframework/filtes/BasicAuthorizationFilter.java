package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.BasicAuthorization;
import com.bananapilot.samplespringauthenticationframework.types.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BasicAuthorizationFilter extends Filter {

    private BasicAuthorization annotation;

    public BasicAuthorizationFilter(Jws<Claims> claimsJws, BasicAuthorization annotation) {
        super(claimsJws);
        this.annotation = annotation;
    }

    public BasicAuthorizationFilter(Jws<Claims> jws) {
        super(jws);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (claimsJws == null) {
            response.setStatus(401);
        }
        User user = User.UserBuilder.anUser()
                .withUsername(claimsJws.getBody().getSubject())
                .withId(claimsJws.getBody().get("user-id", Integer.class))
                .withRoles(claimsJws.getBody().get("user-roles", List.class))
                .build();
        String[] annotationRoles = annotation.roles();
        for (String role: annotationRoles) {
            if(user.getRoles().contains(role)){
                response.addHeader("Authorization", "authorized");
                response.setStatus(200);
                return;
            }
        }
        response.setStatus(403);
    }
}
