package com.bananapilot.samplespringauthenticationframework.config;

import com.bananapilot.samplespringauthenticationframework.filtes.LoginFilter;
import com.bananapilot.samplespringauthenticationframework.filtes.MethodDetector;
import com.bananapilot.samplespringauthenticationframework.filtes.MethodFilter;
import com.bananapilot.samplespringauthenticationframework.service.UserService;
import com.bananapilot.samplespringauthenticationframework.service.UserServiceDefaultImpl;
import com.bananapilot.samplespringauthenticationframework.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = { "framework.jwt.hashKey", "framework.jwt.advice", "framework.jwt.timeToExpire" })
public class SampleSpringAuthenticationFrameworkConfig {
    private static final Logger logger = LoggerFactory.getLogger(SampleSpringAuthenticationFrameworkConfig.class);

    @Bean
    public MethodDetector methodDetector() {
        logger.info("Creating a MethodDetector Bean");
        return new MethodDetector();
    }

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

    @Bean
    public LoginFilter loginFilter() {
        logger.info("Creating a LoginFilter Bean");
        return new LoginFilter();
    }

    @Bean
    public MethodFilter methodFilter() {
        logger.info("Creating a MethodFilter Bean");
        return new MethodFilter();
    }

}
