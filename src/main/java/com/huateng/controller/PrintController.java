package com.huateng.controller;/**
 * @author qs
 * @create 2019-07-24 09:41
 **/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *为了测试response打印html
 * @author shuaion 2019-07-24
 **/
@Controller
@RequestMapping("/test")
public class PrintController {
    @RequestMapping("/player")
    public String player(){
        return "player";
    }

    @RequestMapping("/print")
    public void print(HttpServletRequest request, HttpServletResponse response){
        String html = "<table border=\"1\">\n" +
                "  <tr>\n" +
                "    <th>Month</th>\n" +
                "    <th>Savings</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>January</td>\n" +
                "    <td>$100</td>\n" +
                "  </tr>\n" +
                "</table>";
        PrintWriter writer = null;
        try {
            writer  = response.getWriter();
            writer.print("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

}
