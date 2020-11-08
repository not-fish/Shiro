package com.xilo.shiro.realms;

import com.xilo.shiro.entity.Role;
import com.xilo.shiro.entity.User;
import com.xilo.shiro.service.UserService;
import com.xilo.shiro.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

/**
 * 注意：Realm本身是没有交给Spring工厂处理的，所以不能使用注入的功能
 *      要另外实现一个工厂的工具类去获取Bean，本项目写了一个 ApplicationContextUtils
 */
public class CustomerRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principalCollection
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("--------授权ing--------");
        //获取身份信息
        String primaryPrincipal = (String)principalCollection.getPrimaryPrincipal();

        //在工厂中获取Service对象
        UserService userService = (UserService)ApplicationContextUtils.getBean("userService");
        //获取该身份信息对应的角色集合
       User user = userService.findUserByUserName(primaryPrincipal);
       List<Role> roles = user.getRoles();
        //如果角色集合为空，则返回空
        if(ObjectUtils.isEmpty(roles)){
            return null;
        }
        //授权（赋予角色）
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        roles.forEach(role->{
            simpleAuthorizationInfo.addRole(role.getRoleName());
        });

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("--------认证ing--------");
        //获取身份信息
        String principal = (String)token.getPrincipal();

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
