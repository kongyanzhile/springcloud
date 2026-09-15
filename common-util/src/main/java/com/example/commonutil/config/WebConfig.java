package com.example.commonutil.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig<br>
 * <p>
 * 作成日：2026/9/11<br>
 * 作成者：秦振兴<br>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private UserContextInterceptor userContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userContextInterceptor).addPathPatterns("/**");
    }
}