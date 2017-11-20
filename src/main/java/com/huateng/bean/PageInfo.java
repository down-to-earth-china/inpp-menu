package com.huateng.bean;

import java.util.Map;

/**
 * 分页信息
 *
 * @author Administratoron 2017/2/8
 **/
public class PageInfo extends Page{

    /**
     * 统计数据
     */
    private Map<String,Object> statisticalDatas;

    public Map<String, Object> getStatisticalDatas () {
        return statisticalDatas;
    }

    public void setStatisticalDatas (Map<String, Object> statisticalDatas) {
        this.statisticalDatas = statisticalDatas;
    }
}
