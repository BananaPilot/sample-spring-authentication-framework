package com.bananapilot.samplespringauthenticationframework.service;

import com.bananapilot.samplespringauthenticationframework.repo.UserDao;
import com.bananapilot.samplespringauthenticationframework.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceDefaultImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public User getUser(String username, String password) {
        Assert.notNull(userDao, "UserDao is null. Make sure to declare a UserDao as a spring bean");
        return userDao.getUserByUsername(username);
    }
}
