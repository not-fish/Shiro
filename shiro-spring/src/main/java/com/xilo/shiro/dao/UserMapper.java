package com.xilo.shiro.dao;

import com.xilo.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int addUser(User user);
    User findByUserName(@Param("username") String userName);
}
