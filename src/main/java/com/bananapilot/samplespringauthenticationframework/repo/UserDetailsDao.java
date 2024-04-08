package com.bananapilot.samplespringauthenticationframework.repo;

import com.bananapilot.samplespringauthenticationframework.types.UserDetails;

public interface UserDetailsDao {
    UserDetails getUserByUsername(String username);
}
