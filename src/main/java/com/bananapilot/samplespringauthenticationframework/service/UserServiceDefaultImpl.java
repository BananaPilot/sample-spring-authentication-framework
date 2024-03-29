package com.bananapilot.samplespringauthenticationframework.service;

import com.bananapilot.samplespringauthenticationframework.IncorrectPasswordException;
import com.bananapilot.samplespringauthenticationframework.repo.UserDao;
import com.bananapilot.samplespringauthenticationframework.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDefaultImpl implements UserService{
    UserDao userDao;

    public UserServiceDefaultImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public User authenticate(String username, String password) throws IncorrectPasswordException {
        User user = userDao.getUserByUsername(username);
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new IncorrectPasswordException("inserted password is incorrect");
    }
}
