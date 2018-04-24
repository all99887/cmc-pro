package com.higitech.cmcpro.admin.interceptor;

import cn.hutool.core.util.StrUtil;
import com.higitech.cmcpro.admin.cache.SessionCache;
import com.higitech.cmcpro.admin.consts.NameConsts;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * 权限拦截器
 * 提供简单的权限拦截，约定每个功能模块有不同的前缀，按照前缀判断是否有权限
 * @author liuyanxiang
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionCache sessionCache;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = request.getHeader("cmcProToken");
        if(StrUtil.isEmpty(token)){
            response.setStatus(401);
            response.setHeader("Access-Control-Expose-Headers", "auth-result");
            response.setHeader("auth-result", "notLogin");
            return false;
        }
        CmcUser adminUser = sessionCache.get(token, NameConsts.SessionKeys.USER);
        String contextPath = request.getContextPath();
        if (adminUser == null) {
            response.setStatus(401);
            response.setHeader("Access-Control-Expose-Headers", "auth-result");
            response.setHeader("auth-result", "notLogin");
            return false;
        }

        if(1 == adminUser.getUserId()){
            return true;
        }

        String uri = request.getRequestURI();
        if(StrUtil.isNotEmpty(contextPath)){
            uri = StrUtil.subAfter(uri, contextPath, true);
        }
        if(uri.startsWith("/common/")){
            return true;
        }

        Set<String> urlPreSet = sessionCache.get(token, NameConsts.SessionKeys.USER_PERMISSION);
        String preUri = StrUtil.subBefore(uri, "/", true);
        if(!urlPreSet.contains(preUri)){
            response.setStatus(401);
            response.setHeader("Access-Control-Expose-Headers", "auth-result");
            response.setHeader("auth-result", "noPermission");
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
