package com.xilo.shiro.service;

import com.xilo.shiro.entity.Role;
import com.xilo.shiro.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User findByUsername(String userName);
    User findUserByUserName(String userName);
}
