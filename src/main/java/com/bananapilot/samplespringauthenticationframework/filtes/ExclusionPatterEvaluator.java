package com.bananapilot.samplespringauthenticationframework.filtes;

import com.bananapilot.samplespringauthenticationframework.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.List;

public class ExclusionPatterEvaluator {

    private List<String> antPaths;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    public boolean evaluateExclusion(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        for (String path : antPaths) {
            if (pathMatcher.match(path, requestUri)) {
                request.setAttribute(Constants.SKIP_AUTHORIZATION_FILTER_CHAIN_ATTRIBUTE, Boolean.TRUE);
                return true;
            }
        }
        request.setAttribute(Constants.SKIP_AUTHORIZATION_FILTER_CHAIN_ATTRIBUTE, Boolean.FALSE);
        return false;

    }

    public ExclusionPatterEvaluator mustExcludeAntPathMatchers(String...antPath) {
        this.antPaths = Arrays.asList(antPath);
        return this;
    }

}
