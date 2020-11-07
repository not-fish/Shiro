package com.xilo.shiro.controller;

import com.xilo.shiro.entity.User;
import com.xilo.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping("")
    public String hello(){
        userService.findByUsername("a");
        return "hello";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logining")
    public String loginCheck(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username,password));
            return "redirect:/user/index";
        }catch (UnknownAccountException e){
            System.out.println("用户不存在");
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("登录失败");
        return "redirect:/user/login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/registering")
    public String registerCheck(User user){
        System.out.println("用户名："+user.getUserName());
        System.out.println("密码："+user.getUserName());
        try {
            userService.register(user);
            return "redirect:/user/login";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("注册失败");
            return "redirect:/user/register";
        }


    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/user/login";
    }
}
