package com.bananapilot.samplespringauthenticationframework.repo;

import com.bananapilot.samplespringauthenticationframework.types.User;
public interface UserDao {

    User getUserByUsername(String username);
}
