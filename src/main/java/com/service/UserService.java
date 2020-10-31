package com.service;

import com.entity.User;

public interface UserService {
    void save(User user);

    User findByUserName(String username);
}
