package com.huateng.controller;

import com.huateng.bean.PageInfo;
import com.huateng.entity.User;
import com.huateng.service.IUserService;
import com.huateng.utils.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author shuaion 2017/7/13
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/success")
    public String success(){
        return "success";
    }
    @RequestMapping("/getuser")
    public String getuser(Model model,HttpServletRequest request){
        User user = new User();
        user.setName("张三");
        user.setAge(12);
        user.setAddress("中国长春");
        userService.insertUser(user);
        return "index";
    }
    @RequestMapping("/export")
    @ResponseBody
    public String export(HttpServletResponse response, HttpServletRequest request){
        userService.exoprt(request,response);
        logger.info("数据导出成功");
        return "success";
    }
    @RequestMapping("/userList")
    public String userList(){
        return "loan/loan_records";
    }

    @ResponseBody
    @RequestMapping(value = "/getLoanRecords",method = RequestMethod.POST)
    public String getDatas(int rows, int page){
        logger.info("数据查询");
        PageInfo pageContent = new PageInfo ();
        pageContent.setPageSizes (rows);
        pageContent.setCurPage (page);
        try{
            pageContent = userService.getLoanRecords(pageContent);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JsonUtil.toJSONString (pageContent);
    }
    @ResponseBody
    @RequestMapping("upload")
    public String fileUpload(@RequestParam MultipartFile file) throws UnsupportedEncodingException {
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        if (!type.equals("txt")){
            return JsonUtil.toJSONString("文件类型不正确");
        }
        try {
            file.transferTo(new File("/Users/shuai/"+ new Date().getTime()+".txt"));

            File[] files = new File("/Users/shuai/").listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return false;
                }
            });
            //String ss = IOUtils.toString(file.getInputStream(),"UTF-8");
            //System.out.println(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/inserts")
    public Object insetList(){
        userService.insertList();
        return "success";
    }
}
