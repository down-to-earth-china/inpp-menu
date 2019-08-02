package com.huateng.utils;
/**
 * @author qs
 * @create 2019-07-31 11:23
 **/

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shuaion 2019-07-31
 **/
public class FreemarkerUtils {


    public void dofreemarker(){

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username","小五");

        String name = "ftldemo.ftl";
        Template template = null;
        Configuration configuration = new Configuration();

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("src/main/resources/htmls/1.html");
            configuration.setDirectoryForTemplateLoading(new File("/Users/shuai/develop/workspace_ht/inpp-menu/target/classes/ftl/"));
            template = configuration.getTemplate(name);
            template.process(map,fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
       new FreemarkerUtils().dofreemarker();
    }

}
