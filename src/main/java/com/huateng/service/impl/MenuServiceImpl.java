package com.huateng.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.huateng.bean.PageInfo;
import com.huateng.controller.status.ResponseStatus;
import com.huateng.dao.MenuMapper;
import com.huateng.entity.Menu;
import com.huateng.service.BaseService;
import com.huateng.service.IMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author shuaion 2017/12/1
 **/
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void addMenu(Menu menu) {
        int id = menuMapper.insert(menu);
    }

    @Override
    public List<Menu> getParentMenu() {

        return menuMapper.getParentMenu();
    }

    @Override
    public ResponseStatus delMenu(String menuId) {
        if(StringUtils.isEmpty(menuId)){
            return new ResponseStatus(ResponseStatus.FLAG.FAIL,"删除菜单id为空");
        }
        List<String> ids = Arrays.asList(menuId.split(","));
        if (ids.isEmpty()){
           return new ResponseStatus(ResponseStatus.FLAG.FAIL,"删除菜单id为空");
        }
        menuMapper.delmenu(ids);
        menuMapper.delUserMenu(ids);
        return new ResponseStatus(ResponseStatus.FLAG.SUCCESS);
    }

    @Override
    public PageInfo getPage(PageInfo page) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        int counts = menuMapper.getRecordsCount (params);
        int index = page.calcuteIndex (counts);
        params.put ("index", index);
        params.put ("pageSizes", page.getPageSizes ());
        page.setViewJsonData (menuMapper.getRecords (params));
        return page;
    }

}
