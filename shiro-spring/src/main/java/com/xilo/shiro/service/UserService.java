package com.xilo.shiro.service;

import com.xilo.shiro.entity.User;

public interface UserService {
    void register(User user);
    User findByUsername(String userName);
}
