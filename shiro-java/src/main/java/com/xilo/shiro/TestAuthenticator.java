package com.xilo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @author Peko.Lai
 */
public class TestAuthenticator {
    public static void main(String[] args) {
        //1、创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        //3、给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4、关键对象 Subject 主体
        Subject subject = SecurityUtils.getSubject();
        //5、创建令牌
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

    }
}
