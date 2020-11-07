package com.xilo.shiro.service.impl;

import com.xilo.shiro.dao.UserMapper;
import com.xilo.shiro.entity.User;
import com.xilo.shiro.service.UserService;
import com.xilo.shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public void register(User user){
        System.out.println("开始注册");
        //明文密码进行 MD5 + salt + hash 处理
        String salt = SaltUtils.getSalt(8);
        System.out.println("salt = "+salt);
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getUserPassword(),salt,1024);
        user.setUserPassword(md5Hash.toHex());
        System.out.println("password = "+md5Hash.toHex());
        int i = userMapper.addUser(user);
        if(i==0){
            System.out.println("addUser 失败");
        }
    }

    @Override
    public User findByUsername(String userName){
        User user = userMapper.findByUserName(userName);
        return  user;
    }

}
