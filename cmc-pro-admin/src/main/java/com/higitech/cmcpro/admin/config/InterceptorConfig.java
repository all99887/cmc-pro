package com.higitech.cmcpro.admin.config;

import com.higitech.cmcpro.admin.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lyx on 2017/4/10.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.do")
                // swagger-ui
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/webjars/springfox-swagger-ui/**")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/swagger/**")
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/configuration/**")
                .excludePathPatterns("/");
        super.addInterceptors(registry);
    }

}
