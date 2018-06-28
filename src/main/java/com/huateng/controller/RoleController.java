package com.huateng.controller;

import com.huateng.bean.PageInfo;
import com.huateng.dao.RoleMapper;
import com.huateng.entity.Role;
import com.huateng.entity.User;
import com.huateng.service.IRoleService;
import com.huateng.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author shuaion 2017/12/6
 **/
@Controller
@RequestMapping("role")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private IRoleService roleService;
    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping("rIndex")
    public String rIndex(){
        return "system/roleIndex";
    }
    @ResponseBody
    @RequestMapping("/getRoles")
    public Object getRoles(int rows,int page){
        PageInfo pageContent = new PageInfo ();
        pageContent.setPageSizes (rows);
        pageContent.setCurPage (page);
        try{
            pageContent = roleService.getPage(pageContent);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JsonUtil.toJSONString (pageContent);
    }
    @RequestMapping("/addRole")
    public String addRole(Role role){
        roleMapper.insert(role);
        return "redirect:rIndex";
    }

    @RequestMapping("/rolePermiss")
    public String userMenu(Model model, int roleId){
        Role role = roleMapper.selectByPrimaryKey(roleId);
        model.addAttribute("role",role);

        return "system/rolePermiss";
    }
    @ResponseBody
    @RequestMapping("/savePermiss")
    public Object saveUm(String permissIds,int roleId){
        return roleService.savePermiss(permissIds,roleId);
    }

}
