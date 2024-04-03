package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.service.UserService;
import com.bananapilot.samplespringauthenticationframework.types.User;
import com.bananapilot.samplespringauthenticationframework.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
@Order(0)
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().equals("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        User user = userService.getUser(params.get("username")[0], params.get("password")[0]);
        if (user == null) {
            response.sendError(403);
            return;
        }
        response.setHeader("Authorization", jwtUtils.getJWT(
                params.get("username")[0],
                Integer.parseInt(params.get("id")[0]),
                params.get("roles")[0]));
        response.setStatus(200);
    }
}
