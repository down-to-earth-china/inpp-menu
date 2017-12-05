package com.huateng.service;

import com.huateng.entity.Menu;

import java.util.List;

public interface IMenuService extends BaseService{

    void addMenu(Menu menu);

    List<Menu> getParentMenu();

}
