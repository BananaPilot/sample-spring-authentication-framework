package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.filtes.annotations.BasicAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.FloorLevelAuthorization;
import com.bananapilot.samplespringauthenticationframework.filtes.annotations.NoAuthorization;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.web.method.HandlerMethod;

public class FilterFactory {
    public static Filter doFactory(Jws<Claims> jwtToken, HandlerMethod handlerMethod) {
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
