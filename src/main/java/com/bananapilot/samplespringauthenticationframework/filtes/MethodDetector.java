package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Component
@Order(1)
public class MethodDetector extends OncePerRequestFilter {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired(required = false)
    ExclusionPatterEvaluator exclusionPatterEvaluator;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(exclusionPatterEvaluator == null) {
            return false;
        } else {
            return exclusionPatterEvaluator.evaluateExclusion(request);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean(Constants.HANDLER_METHOD);
        HandlerExecutionChain handlerExeChain;
        try {
            handlerExeChain = handlerMapping.getHandler(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assert.notNull(handlerExeChain, "handlerExeChain is null");
        request.setAttribute(Constants.HANDLER_METHOD, handlerExeChain.getHandler());
        filterChain.doFilter(request, response);
    }
}
