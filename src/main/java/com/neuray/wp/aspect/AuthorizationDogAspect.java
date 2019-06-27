package com.neuray.wp.aspect;

import cn.hutool.http.HttpUtil;
import com.neuray.wp.annotation.AuthorizationDog;
import com.neuray.wp.controller.LoginController;
import com.neuray.wp.entity.SysResRight;
import com.neuray.wp.exception.AuthorizationException;
import com.neuray.wp.exception.LoginException;
import com.neuray.wp.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.reflect.generics.tree.TypeSignature;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 权限狗切面拦截处理
 * 时间 2019/2/15
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Aspect
@Component
@Slf4j
public class AuthorizationDogAspect {

    @Autowired
    private RedisTemplate redisTemplate;


    @Pointcut("@annotation(com.neuray.wp.annotation.AuthorizationDog)")
    public void annotationPoinCut() {
    }

    @Before("annotationPoinCut()")
    public void before(JoinPoint joinPoint) {
//        System.out.println("之前输出2");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = (String) request.getAttribute("token");
        String uri = request.getRequestURI();

        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME + token);

        if (loginUser == null) {
            log.error("当前用户未登录");
            throw new LoginException();
        }
        AtomicBoolean discharged = new AtomicBoolean(false);
        TypeSignature typeSignature = (TypeSignature) joinPoint.getSignature();
        AuthorizationDog authorizationDog = null;
        if (typeSignature != null) {
            authorizationDog = typeSignature.getClass().getAnnotation(AuthorizationDog.class);
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (methodSignature != null) {
            authorizationDog=methodSignature.getMethod().getAnnotation(AuthorizationDog.class);
        }

        if(authorizationDog!=null) {
            if (authorizationDog.ignore()) {
                return;
            } else {
                String[] strings = authorizationDog.specific();
//                Set<SysResRight> sysResRightSet = loginUser.getSysResRights();
//                if (strings != null) {
//                    sysResRightSet.stream().forEach(sysResRight -> {
//                        for (int i = 0; i < strings.length; i++) {
//                            if (sysResRight.getResUri().indexOf(strings[i]) > -1) {
//                                discharged.set(true);
//                            }
//                        }
//                    });
//                } else {
//                    sysResRightSet.stream().forEach(sysResRight -> {
//                        if (sysResRight.getResUri().indexOf(uri) > -1) {
//                            discharged.set(true);
//                        }
//                    });
//                }
            }
        }
        if(!discharged.get())
            throw new AuthorizationException();

    }
}
