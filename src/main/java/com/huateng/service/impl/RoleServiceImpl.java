package com.huateng.service.impl;

import com.huateng.bean.PageInfo;
import com.huateng.dao.RoleMapper;
import com.huateng.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuaion 2017/12/6
 **/
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo getPage(PageInfo page) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        int counts = roleMapper.getRecordsCount (params);
        int index = page.calcuteIndex (counts);
        params.put ("index", index);
        params.put ("pageSizes", page.getPageSizes ());
        page.setViewJsonData (roleMapper.getRecords (params));
        return page;
    }

    @Override
    public int savePermiss(String permissIds, int roleId) {

        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String[] permissid = permissIds.split(",");
        for (String pId:permissid){
            Map<String,Object> map = new HashMap<String, Object>();
            if(!StringUtils.isEmpty(pId)){
                map.put("roleCode",roleId);
                map.put("perCode",pId);
                list.add(map);
            }
        }
        return roleMapper.insertPermiss(list);
    }
}
