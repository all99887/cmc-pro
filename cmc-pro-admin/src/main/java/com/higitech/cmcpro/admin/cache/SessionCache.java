package com.higitech.cmcpro.admin.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * ehcache模拟的session
 */
@Component
public class SessionCache {

    @Autowired
    private EhCacheCacheManager cacheManager;

    private final String cacheName = "session";

    private Cache sessionCache;

    @PostConstruct
    private void init(){
        sessionCache = cacheManager.getCacheManager().getCache(cacheName);
    }

    public <T> T get(String token, String key){
        Element element = sessionCache.get(token);
        if(element == null){
            return null;
        }
        Map<String, T> map = (Map<String, T>)element.getObjectValue();
        return map.get(key);
    }

    public <T> T set(String token, String key, T obj){
        Element element = sessionCache.get(token);
        Map<String, T> map;
        if(element == null){
            map = new HashMap<String, T>();
        } else {
            map =  (Map<String, T>)element.getObjectValue();
        }
        map.put(key, obj);

        Element elementStore = new Element(token, map);
        sessionCache.put(elementStore);
        return obj;
    }

    public void remove(String token, String key){
        Element element = sessionCache.get(token);
        if(element == null){
        } else {
            Map<String, Object> map =  (Map<String, Object>)element.getObjectValue();
            map.remove(key);
            Element elementStore = new Element(token, map);
            sessionCache.put(elementStore);
        }
    }

    public void invalidate(String token){
        sessionCache.remove(token);
    }



}
