package com.shuai.service;

import com.huateng.entity.User;
import com.huateng.service.IUserService;
import org.apache.shiro.codec.Base64;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 业务层代码测试
 *
 * @author shuaion 2017/7/14
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class Test {

    @Autowired
    private IUserService userService;

    //@org.junit.Test
    public void getusers(){
        userService.getUserById(1);
    }
   // @org.junit.Test
    //声明式注解 添加事务传播行为
   // @Transactional(propagation = Propagation.REQUIRED)
    public void testTransManager(){
        User user = new User();
        user.setAddress("北京市朝阳区");
        user.setAge(10);
        user.setName("帅哥");
        userService.insertUser(user);
        User user1 = userService.getUserById(4);
        System.out.println(user1.toString());
    }
    @org.junit.Test
    public void testreids(){
        /*redisUtils.set("time","2017-07-17");
        String time = redisUtils.get("time");
        System.out.println("redis缓冲获取值："+time);
        System.out.println(redisUtils.keys("*"));*/
    }
    @org.junit.Test
    public void cacheTest(){

    }

    public static void main(String[] args) {
        System.out.println(String.valueOf(Base64.decode("1tC/xrDYs8ey+sa3emtiYw==")));
    }
}
