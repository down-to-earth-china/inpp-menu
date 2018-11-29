package com.huateng.controller;

import com.huateng.entity.User;
import com.huateng.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简单登录
 *
 * @author shuaion 2017/11/13
 **/
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login"/*, method = RequestMethod.POST*/)
    public String login(HttpServletRequest request, User user) {

        HttpSession session = request.getSession();

        session.setAttribute("sessType","come from HttpSession");

        Session shiroSession = SecurityUtils.getSubject().getSession();

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated() && !currentUser.isRemembered()){

            UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());

            try {
                currentUser.login(token);
                token.setRememberMe(true);
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                request.setAttribute("error","账号或密码错误");
                return "forward:/index";
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                request.setAttribute("error","账号或密码错误");
                return "forward:/index";
            } catch (ExcessiveAttemptsException e) {
                e.printStackTrace();
                request.setAttribute("error","账号或密码错误");
                return "forward:/index";
            }catch (LockedAccountException e){
                e.printStackTrace();
                request.setAttribute("error","账号被锁定");
                return "forward:/index";
            }catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error","登录异常");
                return "forward:/index";
            }
        }
        //currentUser.getPrincipals();
        //登录成功之后 获取菜单
        List<Map<String, Object>> menus = userService.getMenuByUser(currentUser.getPrincipal().toString());
        List<Map<String, Object>> parents = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> childs = null;
        for (Map<String, Object> menu : menus) {
            if ("0".equals(menu.get("parent_id"))) {
                childs = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < menus.size(); i++) {
                    if (menu.get("id").toString().equals(menus.get(i).get("parent_id"))) {
                        childs.add(menus.get(i));
                    }
                }
                menu.put("childs", childs);
                parents.add(menu);
            }
        }
        /*Session session1 = SecurityUtils.getSubject().getSession();
        session1.setAttribute("menus1",parents);

        HttpSession session = request.getSession();
        session.setAttribute("menus",parents);*/

        request.setAttribute("menus", parents);
        request.getSession().setAttribute("menus", parents);
        return "home";
    }
    @RequestMapping("/index")
    public String index(){

        return "index";
    }
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, User user) {

        SecurityUtils.getSubject().logout();

        return "redirect:/user/index";
    }

    @RequestMapping("/unauthor")
    public String unauthor(HttpServletRequest request, User user) {

        SecurityUtils.getSubject().logout();

        return "redirect:/unauthor";
    }
}
