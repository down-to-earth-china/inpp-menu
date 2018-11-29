package com.huateng.controller;

import com.huateng.bean.PageInfo;
import com.huateng.controller.status.ResponseStatus;
import com.huateng.dao.UserMapper;
import com.huateng.entity.User;
import com.huateng.service.IUserService;
import com.huateng.smtp.SmtpClient;
import com.huateng.utils.JsonUtil;
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
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/success")
    public String success(){
        return "success";
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
        return "system/userlist";
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
    @RequestMapping("/userMenu")
    public String userMenu(Model model, int userId){
        User user = userService.getUserById(userId);
        List<Integer> menuIds = userMapper.getUserMenus(userId);
        model.addAttribute("user",user);
        model.addAttribute("menuIds",menuIds);
        return "system/userMenu";
    }

    @ResponseBody
    @RequestMapping("/saveUm")
    public Object saveUm(String menuIds,int userId){
        return userService.saveUm(menuIds,userId);
    }


    @RequestMapping("/delUser")
    @ResponseBody
    public Object delUser(int userId){
        userService.delUser(userId);
        SmtpClient client = new SmtpClient();
        client.setSubject("send email to 乔帅");
        client.syncSendMessage("恭喜恭喜");
        return new ResponseStatus(ResponseStatus.FLAG.SUCCESS);
    }
    @RequestMapping("addUser")
    @ResponseBody
    public Object addUser(User user){
        userService.insertUser(user);
        return new ResponseStatus(ResponseStatus.FLAG.SUCCESS);
    }

    public static void main(String[] args) {
        float x = 10.02f;
        System.out.println("x*x="+x*x);

        int a = 3;
        System.out.println("a<<3="+(a<<3));
        System.out.println("a>>3="+(a>>3));

        System.out.println("1<<30 = "+(1<<30));

    }
}
