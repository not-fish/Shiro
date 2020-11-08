package com.xilo.shiro.controller;

import com.xilo.shiro.entity.User;
import com.xilo.shiro.service.UserService;
import com.xilo.shiro.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public String loginCheck(String username,String password,String verifyCode,HttpSession session){
        //验证码比较
        String code = (String) session.getAttribute("verifyCode");
        if(!code.equalsIgnoreCase(verifyCode)){
            System.out.println("验证码错误");
            return "redirect:/user/login";
        }
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

    @RequestMapping("manage")
    @RequiresRoles("admin")
    public String manage(){
        System.out.println("权限符合");
        return "redirect:/user/index";
    }

    /**
     * 验证码方法
     */
    @RequestMapping("getimage")
    public void getImage(HttpSession session, HttpServletResponse response)throws IOException {
        //生成验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //验证码放入Session
        session.setAttribute("verifyCode",verifyCode);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220,60,os,verifyCode);

    }

}
