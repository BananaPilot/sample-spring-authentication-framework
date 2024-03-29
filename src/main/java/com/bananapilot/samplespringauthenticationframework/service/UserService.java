package com.bananapilot.samplespringauthenticationframework.service;

import com.bananapilot.samplespringauthenticationframework.IncorrectPasswordException;
import com.bananapilot.samplespringauthenticationframework.types.User;

public interface UserService {
    User authenticate(String username, String password) throws IncorrectPasswordException;
}
