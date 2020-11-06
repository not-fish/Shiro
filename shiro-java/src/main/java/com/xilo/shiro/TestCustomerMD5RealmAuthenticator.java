package com.xilo.shiro;

import com.xilo.realm.CustomerMD5Realm;
import com.xilo.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class TestCustomerMD5RealmAuthenticator {
    public static void main(String[] args) {
        //1、创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、创建Realm
        CustomerMD5Realm realm = new CustomerMD5Realm();

        //3、设置Realm使用hash凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        //散列1024次
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);

        //4、给安全管理器设置【自定义realm】
        securityManager.setRealm(realm);
        //5、给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //6、关键对象 Subject 主体
        Subject subject = SecurityUtils.getSubject();
        //7、创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("jack","001");

        //用户登录（进行验证）
        try{
            subject.login(token);
            System.out.println("认证状态:"+subject.isAuthenticated());
        }catch (UnknownAccountException e){
            System.out.println("用户"+token.getUsername()+"不存在");
        }catch (IncorrectCredentialsException e){
            System.out.println("用户"+token.getUsername()+"密码错误");
        }catch (Exception e){
            e.printStackTrace();
        }

        //验证授权
        if(subject.isAuthenticated()){
            //查看 subject 是否有 admin 角色
            System.out.println(subject.hasRole("admin"));
            //查看 subject 是否有 user1 和 user2 角色
            System.out.println(subject.hasAllRoles(Arrays.asList("user1","user2")));
            //查看 subject 是否有 admin 或 user1 或 user2 角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin","user1","user2"));
            for(boolean bo : booleans){
                System.out.println(bo);
            }

            //基于权限字符串的访问控制（资源标识符：操作：资源类型）
            System.out.println("权限:"+subject.isPermitted("user1:select:xx1"));
            System.out.println("权限:"+subject.isPermittedAll("user1:select:xx1","user1:create:xx1"));
            booleans = subject.isPermitted("user1:select:xx1","user1:create:xx1","user1:delete:*");
            for(boolean bo : booleans){
                System.out.println(bo);
            }

        }
    }
}
