package com.xilo.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomerMD5Realm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("doGetAuthorizationInfo---"+"进行认证："+principalCollection.getPrimaryPrincipal());

        //添加角色
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user1");

        //将数据库中的查询权限信息赋值给权限对象
        simpleAuthorizationInfo.addStringPermission("user1:select:xx1");
        simpleAuthorizationInfo.addStringPermission("user1:create:xx2");
        simpleAuthorizationInfo.addStringPermission("user1:delete:*");

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();
        System.out.println("doGetAuthenticationInfo---用户名："+principal);
        if("jack".equals(principal)){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("jack","923391846b52b2391ba3a3cfca311ab9", ByteSource.Util.bytes("01*1334px"),this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
