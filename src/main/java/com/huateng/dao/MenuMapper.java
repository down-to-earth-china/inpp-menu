package com.huateng.dao;

import com.huateng.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuMapper extends BaseMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Map<String,Object>> getMenuByUser(String userName);

    List<Menu> getParentMenu();

    void delmenu(@Param("ids") List<String> ids);

    void delUserMenu(@Param("ids") List<String> ids);
}