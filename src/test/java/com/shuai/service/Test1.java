package com.shuai.service;

import com.huateng.entity.User;
import com.huateng.service.IUserService;
import com.huateng.utils.EhcacheUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Set;

/**
 * 业务层代码测试
 *
 * @author shuaion 2017/7/14
 **/
public class Test1 {

    private static ApplicationContext context;

    public static void init(){
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static void main(String[] args) {

        EhcacheUtils ehcacheUtils = EhcacheUtils.initCache("user-cache");

        Set keys = ehcacheUtils.getKeys();

        System.out.println("0.cache="+ehcacheUtils.getCache());

        System.out.println("1.keys="+keys);

        ehcacheUtils.put("date",new Date().getTime());
        ehcacheUtils.put("time",new Date().getTime());

        Set keys1 = ehcacheUtils.getKeys();

        System.out.println("3.keys1="+keys1);

        Object date = ehcacheUtils.get("date");

        System.out.println("4.date="+date);

        ehcacheUtils.remove("date");

        Set keys2 = ehcacheUtils.getKeys();

        System.out.println("keys2="+keys2);
        /*init();
        EhCacheManager cacheManager = (EhCacheManager) context.getBean("cacheManager");
        System.out.println(cacheManager.getCacheManager().getCache("user-cache"));*/


       /* IUserService userService = (IUserService) context.getBean("userServiceImpl");
        User user = userService.getUserById(17);
        System.out.println(user.toString());*/
    }
}
