package com.higitech.cmcpro.admin.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionCacheTest {

    @Autowired
    private SessionCache sessionCache;

    @Test
    public void sessionCacheTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("1", "1");
        map.put("2", "2");
        sessionCache.set("111", "111", map);
        Map<String, Object> map2 = sessionCache.get("111", "111");
        System.out.println(map2);
        sessionCache.remove("111", "111");
        Map<String, Object> map3 = sessionCache.get("111", "111");
        System.out.println(map3);
    }

}
