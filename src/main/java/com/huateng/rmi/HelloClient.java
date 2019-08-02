package com.huateng.rmi;/**
 * @author qs
 * @create 2019-07-23 09:11
 **/

import java.rmi.Naming;

/**
 * 测试RMI 服务端在spring-mybatis项目中
 * @author shuaion 2019-07-23
 **/
public class HelloClient {

    public static void test() {
//        HelloService helloService = (HelloService) Naming.lookup("//127.0.0.1:1100/hello");

    }

    public static void main(String[] args) {
        test();
    }
}
