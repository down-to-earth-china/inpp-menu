package com.huateng.dao;


import com.huateng.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Map<String,Object>> getRecords(Map<String,Object> params)throws Exception;

    int getRecordsCount(Map<String,Object> params)throws Exception;

    void insertList(List<String> list);

    User getUserByName(String name);

    List<Integer> getUserMenus(int userId);

    int insertUms(List<Map<String,Object>> list);

    void deleteUserMenu(int userId);
}