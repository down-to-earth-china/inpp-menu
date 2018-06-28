package com.huateng.service;

import com.huateng.bean.PageInfo;
import com.huateng.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IUserService{
    void insertUser(User user);
    User getUserById(int id);
    void exoprt(HttpServletRequest request, HttpServletResponse response);
    PageInfo getLoanRecords (PageInfo page) throws Exception;
    List<Map<String,Object>> getMenuByUser(String userName);
    int saveUm(String menuIds,int userId);
}
