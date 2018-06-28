package com.huateng.service.impl;

import com.huateng.bean.PageInfo;
import com.huateng.dao.PermissionMapper;
import com.huateng.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shuaion 2017/12/6
 **/
@Component
public class PermissServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public PageInfo getPage(PageInfo page) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        int counts = permissionMapper.getRecordsCount (params);
        int index = page.calcuteIndex (counts);
        params.put ("index", index);
        params.put ("pageSizes", page.getPageSizes ());
        page.setViewJsonData (permissionMapper.getRecords (params));
        return page;
    }
}
