package com.higitech.cmcpro.admin.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.higitech.cmcpro.admin.modules.*.controller.*Controller.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        if (logger.isDebugEnabled()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("URL", request.getRequestURL().toString());
            logMap.put("HTTP_METHOD", request.getMethod());
            logMap.put("IP", ServletUtil.getClientIP(request));
            logMap.put("CLASS_METHOD", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature
                    ().getName());
            Object[] args = pjp.getArgs();
            Object[] argsFinal = ArrayUtil.filter(args, (Filter<Object>) kvEntry -> !(kvEntry instanceof ServletResponse || kvEntry instanceof HttpSession|| kvEntry instanceof ServletRequest)
            );
            logMap.put("ARGS", argsFinal);
            logger.debug("controllerLog before : {}", JSON.toJSONString(logMap));
        }
        Object object = pjp.proceed(); //获取被切函数的 返回值
        if (logger.isDebugEnabled()) {
            logger.debug("controllerLog after : {}", JSON.toJSONString(object));
        }
        return object;
    }
}
