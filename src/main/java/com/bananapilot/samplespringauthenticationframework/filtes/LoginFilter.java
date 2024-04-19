package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.repo.UserDetailsDao;
import com.bananapilot.samplespringauthenticationframework.service.UserDetailsService;
import com.bananapilot.samplespringauthenticationframework.types.UserDetails;
import com.bananapilot.samplespringauthenticationframework.utils.Constants;
import com.bananapilot.samplespringauthenticationframework.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@Order(0)
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserDetailsService userService;

    @Autowired
    UserDetailsDao userDetailsDao;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().contains("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        UserDetails userDetails = userService.checkCredentials(params.get("username")[0], params.get("password")[0]);
        if (userDetails == null) {
            response.sendError(403);
            return;
        }
        response.setHeader(Constants.AUTHORIZATION_HEADER, jwtUtils.getJWT(params.get("username")[0], Long.parseLong(params.get("id")[0]), userDetailsDao.getUserByUsername(params.get("username")[0]).getRoles().toString()));

        response.setStatus(200);
    }
}
