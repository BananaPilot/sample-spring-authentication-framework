package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.BasicAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.FloorLevelAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.NoAuthorization;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;

public class FilterFactory {
    public static OncePerRequestFilter doFactory(String jwtToken, HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(NoAuthorization.class)) {
            return new NoAuthorizationFilter();
        }
        if (handlerMethod.hasMethodAnnotation(BasicAuthorization.class)) {
            return new BasicAuthorizationFilter(jwtToken, handlerMethod.getMethodAnnotation(BasicAuthorization.class));
        }
        if (handlerMethod.hasMethodAnnotation(FloorLevelAuthorization.class)) {
            return new FloorLevelAuthenticationFilter(jwtToken, handlerMethod.getMethodAnnotation(FloorLevelAuthorization.class));
        }
        return null;
    }
}
