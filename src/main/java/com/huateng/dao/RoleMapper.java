package com.huateng.dao;

import com.huateng.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int insertPermiss(List list);
}