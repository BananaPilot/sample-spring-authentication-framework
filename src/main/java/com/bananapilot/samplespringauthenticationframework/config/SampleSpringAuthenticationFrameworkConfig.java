package com.bananapilot.samplespringauthenticationframework.config;


import com.bananapilot.samplespringauthenticationframework.repo.UserDao;
import com.bananapilot.samplespringauthenticationframework.service.UserService;
import com.bananapilot.samplespringauthenticationframework.service.UserServiceDefaultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleSpringAuthenticationFrameworkConfig {
    private static final Logger logger = LoggerFactory.getLogger(SampleSpringAuthenticationFrameworkConfig.class);

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(UserDao userDao) {
        logger.info("Creating the user service");
        return new UserServiceDefaultImpl(userDao);
    }

}
