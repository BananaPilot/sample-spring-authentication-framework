package com.bananapilot.samplespringauthenticationframework.repo;

import com.bananapilot.samplespringauthenticationframework.types.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User getUserByUsername(String username);
}
