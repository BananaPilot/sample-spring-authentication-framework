package com.bananapilot.samplespringauthenticationframework.filtes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class Filter extends OncePerRequestFilter {
    Jws<Claims> claimsJws;

    public Filter(Jws<Claims> claimsJws) {
        this.claimsJws = claimsJws;
    }

    public Filter() {

    }

    public Jws<Claims> getClaimsJws() {
        return claimsJws;
    }
}
