package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.FloorLevelAuthorization;
import com.bananapilot.samplespringauthenticationframework.types.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class FloorLevelAuthenticationFilter extends Filter {

    private final FloorLevelAuthorizationImpl floorLevelAuthorization = new FloorLevelAuthorizationImpl();
    private final FloorLevelAuthorization annotation;

    public FloorLevelAuthenticationFilter(Jws<Claims> claimsJws, FloorLevelAuthorization annotation) {
        super(claimsJws);
        this.annotation = annotation;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (claimsJws == null) {
            response.setStatus(401);
        }
        User user = User.UserBuilder.anUser()
                .withUsername(claimsJws.getBody().getSubject())
                .withId(claimsJws.getBody().get("user-id", Integer.class))
                .withRoles(claimsJws.getBody().get("user-roles", List.class))
                .build();
        String annotationRoles = annotation.floorRole();
        if (floorLevelAuthorization.isFloorRoleEqualOrGreaterTo(annotationRoles, user.getRoles())) {
            response.setHeader("Authorization", "authorized");
            response.setStatus(200);
            return;
        }
        response.setStatus(403);
    }
}
