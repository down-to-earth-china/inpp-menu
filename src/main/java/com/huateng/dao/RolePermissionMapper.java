package com.huateng.dao;

import com.huateng.entity.RolePermission;

import java.util.List;
import java.util.Map;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    List<Map<String,Object>> getPermissionByUser(Integer userId);
}