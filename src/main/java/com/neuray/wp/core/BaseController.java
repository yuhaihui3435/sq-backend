package com.neuray.wp.core;

import com.neuray.wp.controller.LoginController;
import com.neuray.wp.model.LoginUser;
import com.neuray.wp.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public abstract class BaseController {

    @Autowired
    protected RedisCacheService redisCacheService;
    @Autowired
    private RedisTemplate redisTemplate;

    protected LoginUser currLoginUser(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token=(String)request.getAttribute("token");
        LoginUser loginUser=(LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME+token);
        return loginUser;
    }





}
