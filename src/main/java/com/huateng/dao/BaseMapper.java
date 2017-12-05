package com.huateng.dao;

import java.util.List;
import java.util.Map;

/**
 * @author shuaion 2017/12/1
 **/
public interface BaseMapper {


    List<Map<String,Object>> getRecords(Map<String,Object> params)throws Exception;

    int getRecordsCount(Map<String,Object> params)throws Exception;
}
