package com.huateng.controller;

import com.huateng.entity.User;
import com.huateng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 简单登录
 *
 * @author shuaion 2017/11/13
 **/
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    @RequestMapping("login")
    public String login(HttpServletRequest request, User user){

       // User user = userService.getUserById();
        User user1 = new User();
        user.setName("zhangsan");
        request.getSession().setAttribute("loginUser",user1);

        return "forward:/user/home";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, User user){

        request.getSession().removeAttribute("loginUser");

        return "redirect:/index";
    }
}
