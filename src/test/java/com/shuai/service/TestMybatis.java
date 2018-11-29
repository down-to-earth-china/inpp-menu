package com.shuai.service;/**
 * @author qs
 * @create 2018-11-06 14:48
 **/

import com.huateng.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author shuaion 2018/11/6
 **/
public class TestMybatis {

    public static void test(){
        String config = "dbconfig-bf.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(config);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            SqlSession sqlSession = sqlSessionFactory.openSession();
            HashMap hashMap = new HashMap();
            hashMap.put("name","zhangsan");

            User user = sqlSession.selectOne("getUserByName",hashMap);

            System.out.println(user.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        test();
    }
}
