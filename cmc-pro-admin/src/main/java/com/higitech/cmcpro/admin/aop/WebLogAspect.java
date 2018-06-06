package com.higitech.cmcpro.admin.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.higitech.cmcpro.admin.cache.SessionCache;
import com.higitech.cmcpro.admin.component.WebLogComponent;
import com.higitech.cmcpro.admin.consts.NameConsts;
import com.higitech.cmcpro.admin.modules.system.entity.CmcLog;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class WebLogAspect {

    @Autowired
    private SessionCache sessionCache;
    @Autowired
    private WebLogComponent webLogComponent;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.higitech.cmcpro.admin.modules.*.controller.*Controller.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        Object object = pjp.proceed(); //获取被切函数的 返回值

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = pjp.getArgs();
        Object[] argsFinal = ArrayUtil.filter(args, (Filter<Object>) kvEntry -> !(kvEntry instanceof ServletResponse || kvEntry instanceof HttpSession || kvEntry instanceof ServletRequest)
        );

        CmcLog cmcLog = new CmcLog();
        String cmcProToken = request.getHeader("cmcProToken");
        CmcUser cmcUser = sessionCache.get(cmcProToken, NameConsts.SessionKeys.USER);
        if(cmcUser != null){
            cmcLog.setOperatorId(cmcUser.getUserId());
            cmcLog.setOperatorRealName(cmcUser.getRealName());
        }
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        if(StrUtil.isNotEmpty(contextPath)){
            uri = StrUtil.subAfter(uri, contextPath, true);
        }

        cmcLog.setOperatorIp(ServletUtil.getClientIP(request));
        cmcLog.setUrl(uri);
        cmcLog.setParams(JSON.toJSONString(argsFinal));
        cmcLog.setReturnResult(JSON.toJSONString(object));
        cmcLog.setOperateTime(new Date());
        logger.debug("controllerLog before : {}", JSON.toJSONString(cmcLog));
        if(logFilter(uri)){
            webLogComponent.pushLog(cmcLog);
        }


        if (logger.isDebugEnabled()) {
            logger.debug("controllerLog after : {}", JSON.toJSONString(object));
        }
        return object;
    }

    private boolean logFilter(String uri){
        if(uri.startsWith("/common/") || uri.startsWith("/system/webPicCaptcha.do")
                || uri.endsWith("list") || uri.endsWith("webLogin.do")){
            return false;
        }
        return true;
    }
}
