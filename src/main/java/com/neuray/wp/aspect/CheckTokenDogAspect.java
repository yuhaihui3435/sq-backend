package com.neuray.wp.aspect;

import cn.hutool.core.util.StrUtil;
import com.neuray.wp.exception.LoginException;
import com.neuray.wp.model.MemberLoginDto;
import com.neuray.wp.service.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 网站用于检查用户是否登录的切面
 * 时间 2019/7/31
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Aspect
@Component
@Slf4j
public class CheckTokenDogAspect {
    @Autowired
    private RedisCacheService redisCacheService;



    @Pointcut("@annotation(com.neuray.wp.annotation.CheckTokenDog)")
    public void annotationPoinCut() {
    }

    @Before("annotationPoinCut()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token =  request.getHeader("token");
        if(StrUtil.isBlank(token)){
            throw new LoginException();
        }

        MemberLoginDto memberLoginDto=(MemberLoginDto)redisCacheService.findVal(token);
        if(memberLoginDto==null){
            throw new LoginException();
        }

    }
}
