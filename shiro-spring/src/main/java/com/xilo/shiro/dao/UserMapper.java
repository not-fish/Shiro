package com.xilo.shiro.dao;

import com.xilo.shiro.entity.Role;
import com.xilo.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int addUser(User user);
    User findByUserName(@Param("userName") String userName);
    User findUserByUserName(String userName);
}
