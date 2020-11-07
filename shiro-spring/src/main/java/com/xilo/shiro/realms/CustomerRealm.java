package com.xilo.shiro.realms;

import com.xilo.shiro.entity.User;
import com.xilo.shiro.service.UserService;
import com.xilo.shiro.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

/**
 * 注意：Realm本身是没有交给Spring工厂处理的，所以不能使用注入的功能
 *      要另外实现一个工厂的工具类去获取Bean，本项目写了一个 ApplicationContextUtils
 */
public class CustomerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();
        System.out.println("doGetAuthenticationInfo---用户名："+principal);

        //在工厂中获取Service对象
        UserService userService = (UserService)ApplicationContextUtils.getBean("userService");
        User user = userService.findByUsername(principal);
        //如果没有查询到用户则退出
        if(ObjectUtils.isEmpty(user)){
            return null;
        }

        return new SimpleAuthenticationInfo(user.getUserName(),user.getUserPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());


    }
}
