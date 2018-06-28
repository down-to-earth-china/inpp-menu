package com.huateng.controller;

import com.huateng.bean.PageInfo;
import com.huateng.dao.PermissionMapper;
import com.huateng.entity.Permission;
import com.huateng.service.IPermissionService;
import com.huateng.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shuaion 2017/12/6
 **/
@Controller
@RequestMapping("perm")
public class PermissionController {

    private Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private PermissionMapper permissionMapper;

    @RequestMapping("/pIndex")
    public String pIndex(){
        return "system/permissionIndex";
    }

    @ResponseBody
    @RequestMapping(value = "/getPermissions",method = RequestMethod.POST)
    public Object getPermissions(int rows, int page){
        PageInfo pageContent = new PageInfo ();
        pageContent.setPageSizes (rows);
        pageContent.setCurPage (page);
        try{
            pageContent = permissionService.getPage(pageContent);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JsonUtil.toJSONString (pageContent);
    }

    @RequestMapping("/addPerm")
    public String addPerm(Permission permission){
        permissionMapper.insert(permission);
        return "redirect:pIndex";
    }

}
