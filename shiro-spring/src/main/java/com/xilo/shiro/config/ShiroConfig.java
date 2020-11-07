package com.xilo.shiro.config;


import com.xilo.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //1、创建shiroFilter 负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);


        //配置系统公共资源
        Map<String,String> map = new HashMap<String,String>();
        // anon 请求这个资源不需要认证和授权
        map.put("/user","anon");
        map.put("/user/login","anon");
        map.put("/user/logining","anon");
        map.put("/user/register","anon");
        map.put("/user/registering","anon");
        //配置系统受限资源
        // authc 请求这个资源需要认证和授权
        map.put("/**","authc");

        //默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/user/login");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);



        return shiroFilterFactoryBean;
    }

    //2、创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置 realm
        defaultWebSecurityManager.setRealm(realm);

        return defaultWebSecurityManager;
    }

    //3、创建自定义realm
    @Bean
    public Realm getRealm(){

        CustomerRealm customerRealm = new CustomerRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);

        return customerRealm;
    }

}
