package com.huateng.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shuaion 2017/11/12
 **/
@Service
@EnableScheduling
public class FileParseSche {

   // @Scheduled(fixedRate = 3000)
    public void fileToString(){
        System.out.println("===========定时任务==========="+new Date());
    }
}
