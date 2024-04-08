package com.bananapilot.samplespringauthenticationframework.service;

import com.bananapilot.samplespringauthenticationframework.types.UserDetails;

public interface UserDetailsService {
    UserDetails checkCredentials(String username, String password);
}
