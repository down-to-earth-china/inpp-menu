package com.huateng.controller;

import com.huateng.bean.PageInfo;
import com.huateng.entity.Menu;
import com.huateng.service.IMenuService;
import com.huateng.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shuaion 2017/12/1
 **/
@Controller
@RequestMapping("/system")
public class SystemController {

    private Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/addMenu")
    public String addMenu(Menu menu){
        menuService.addMenu(menu);
        return "redirect:/system/mIndex";
    }

    @RequestMapping("mIndex")
    public String menuIndex(){
        return "system/menuIndex";
    }
    @ResponseBody
    @RequestMapping("/pMenus")
    public Object pMenus(){
        return menuService.getParentMenu();
    }

    @ResponseBody
    @RequestMapping("/getMenus")
    public String getMenus(int rows, int page){
        PageInfo pageContent = new PageInfo ();
        pageContent.setPageSizes (rows);
        pageContent.setCurPage (page);
        try{
            pageContent = menuService.getPage(pageContent);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JsonUtil.toJSONString (pageContent);
    }

    @RequestMapping("userMenu")
    public String userMenu(){

        return "system/userMenu";
    }

}
