package com.bananapilot.samplespringauthenticationframework.service;

import com.bananapilot.samplespringauthenticationframework.repo.UserDetailsDao;
import com.bananapilot.samplespringauthenticationframework.types.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceDefaultImpl implements UserDetailsService {

    @Autowired(required = false)
    UserDetailsDao userDao;

    @Override
    public UserDetails checkCredentials(String username, String password) {
        Assert.notNull(userDao, "UserDao is null. Make sure to declare a UserDao as a spring bean");
        return userDao.getUserByUsername(username);
    }
}
