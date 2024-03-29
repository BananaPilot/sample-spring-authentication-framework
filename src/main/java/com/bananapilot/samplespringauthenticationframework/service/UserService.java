package com.bananapilot.samplespringauthenticationframework.service;

import com.bananapilot.samplespringauthenticationframework.types.User;

public interface UserService {

    User getUser(String username, String password);
}
