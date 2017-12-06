package com.huateng.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * @author shuaion 2017/12/5
 **/
public class BeanUtil {

    public static Object getBean(String name){

        ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        if (applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return applicationContext.getBean(name);
    }
}
