package com.huateng.utils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author shuaion 2017/12/5
 **/
public class EhcacheUtils {

    @Autowired
    public static EhCacheManager cacheManager;

    private final Cache cache;

    public EhcacheUtils(Cache cache) {
        this.cache = cache;
    }

    public static EhcacheUtils initCache(String cacheName){

        if (cacheManager==null){
            cacheManager = (EhCacheManager)BeanUtil.getBean("cacheManager");
        }
        if (StringUtils.isEmpty(cacheName))return null;

        Cache shiroCache = cacheManager.getCache(cacheName);

        if (shiroCache==null){
            cacheManager.getCacheManager().addCache(cacheName);
        }

        return new EhcacheUtils(shiroCache);
    }

    public Set getKeys(){
        return cache.keys();
    }
    public void put(String key,Object object){
        cache.put(key,object);
    }
    public Object get(String key){
        return cache.get(key);
    }

    public void remove(Object key){
        cache.remove(key);
    }
    //清除当前缓存
    public void removeAllCache(){
        cacheManager.getCacheManager().removalAll();
    }
    //清除所有缓存
    public void clearAllCache(){
        cacheManager.getCacheManager().clearAll();
    }

    public Cache getCache() {
        return cache;
    }
}
