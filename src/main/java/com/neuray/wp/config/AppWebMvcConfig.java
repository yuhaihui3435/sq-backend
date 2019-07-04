package com.neuray.wp.config;


import com.neuray.wp.interceptor.AccessControlInterceptor;
import com.neuray.wp.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AccessControlInterceptor accessControlInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/createKaptcha", "/createKaptchaImg", "/cc/**", "/sysConf/loadPic", "/sysUser/loadPic", "/carousel/loadPic", "/artice/loadPic");
//        registry.addInterceptor(accessControlInterceptor).addPathPatterns("/**").excludePathPatterns("/login","/createKaptcha","/createKaptchaImg");
    }
}
