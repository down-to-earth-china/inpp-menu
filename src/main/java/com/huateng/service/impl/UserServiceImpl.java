package com.huateng.service.impl;

import com.huateng.bean.PageInfo;
import com.huateng.dao.MenuMapper;
import com.huateng.dao.UserMapper;
import com.huateng.entity.User;
import com.huateng.excels.ExcelTemplate;
import com.huateng.excels.UserDataExcel;
import com.huateng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuaion 2017/7/14
 **/
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        System.out.println(user.toString());
        return user;
    }

    @Override
    public void exoprt(HttpServletRequest request, HttpServletResponse response){
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setName("张三");
        user.setAddress("BJ");
        users.add(user);
        user.setAge(40);
        User user1 = new User();
        user1.setName("张三1");
        user1.setAddress("BJ");
        user1.setAge(40);
        users.add(user1);
        User user2 = new User();
        user2.setName("张三2");
        user2.setAddress("BJ");
        user2.setAge(40);
        users.add(user2);
        UserDataExcel excel = new UserDataExcel();
        String realpath = request.getRealPath(excel.relativePath);
        ExcelTemplate template = ExcelTemplate.newInstance(realpath);
        try {
            excel.fillExcel(template, users, 0);
           // response.reset();
            excel.setExcelResponse(response, excel.getFileName());
            template.getWorkbook().write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public PageInfo getLoanRecords(PageInfo page) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();

        int counts = userMapper.getRecordsCount (params);
        int index = page.calcuteIndex (counts);
        params.put ("index", index);
        params.put ("pageSizes", page.getPageSizes ());
        page.setViewJsonData (userMapper.getRecords (params));
        return page;
    }

    @Override
    public List<Map<String, Object>> getMenuByUser(String userName) {
        return menuMapper.getMenuByUser(userName);
    }

    @Override
    public int saveUm(String menuIds,int userId) {
        userMapper.deleteUserMenu(userId);
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String[] menuId = menuIds.split(",");
        for (String mId:menuId){
            Map<String,Object> map = new HashMap<String, Object>();
            if(!StringUtils.isEmpty(mId)){
                map.put("menuId",mId);
                map.put("userId",userId);
                list.add(map);
            }
        }
        return userMapper.insertUms(list);
    }

}
