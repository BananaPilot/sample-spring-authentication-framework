package com.bananapilot.samplespringauthenticationframework.config;

import com.bananapilot.samplespringauthenticationframework.service.UserService;
import com.bananapilot.samplespringauthenticationframework.service.UserServiceDefaultImpl;
import com.bananapilot.samplespringauthenticationframework.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SampleSpringAuthenticationFrameworkConfig {
    private static final Logger logger = LoggerFactory.getLogger(SampleSpringAuthenticationFrameworkConfig.class);

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService() {
        logger.info("Creating the user service");
        return new UserServiceDefaultImpl();
    }

    @Bean
    public JWTUtils jwtUtils() {
        logger.info("Creating a JWTUtils Bean");
        return new JWTUtils();
    }

    public void test() {
        System.out.println(jwtUtils().getJWT("fabio", 1, Arrays.asList("ADMIN")));
    }

}
