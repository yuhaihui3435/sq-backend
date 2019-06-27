package com.neuray.wp.aspect;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.controller.LoginController;
import com.neuray.wp.entity.OperationLog;
import com.neuray.wp.exception.LoginException;
import com.neuray.wp.model.LoginUser;
import com.neuray.wp.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;


/**
 * 权限狗切面拦截处理
 * 时间 2019/2/15
 *
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Aspect
@Component
@Slf4j
public class LogDogAspect {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OperationLogService operationLogService;


    @Pointcut("@annotation(com.neuray.wp.annotation.LogDog)")
    public void annotationPoinCut() {
    }

//    @Before("annotationPoinCut()")
//    public void before(JoinPoint joinPoint) {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        String token = (String) request.getAttribute("token");
//        //action路径
//        String uri = request.getRequestURI();
//        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME + token);
//        if (loginUser == null) {
//            log.error("当前用户未登录");
//            throw new LoginException();
//        }
//        LogDog logDog = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogDog.class);
//        //登录人id
//        Long loginId = loginUser.getId();
//        //客户端ip
//        String clientIp = request.getRemoteAddr();
//        //方法名
//        String method = joinPoint.getSignature().getName();
//        //请求参数
//        String params = new JSONArray(joinPoint.getArgs()).toString();
//        //注解参数
//        //日志类型
//        String logType = logDog.logType().getLabel();
//        //访问类型
//        String reqSource = logDog.reqSource().getLabel();
//        //描述
//        String desc = logDog.desc();
//        OperationLog operationLog = new OperationLog();
//        //用户id
//        operationLog.setSysUserId(loginId);
//        //访问数据
//        operationLog.setAccessData("");
//        //访问功能
//        operationLog.setAccessFunction(method);
//        //访问来源
//        operationLog.setAccessSource(clientIp);
//        //访问路径
//        operationLog.setAccessPath(uri);
//        //访问时间
//        operationLog.setAccessTime(new Date());
//        //访问类型
//        operationLog.setAccessType(reqSource);
//        //访问日志类型
//        operationLog.setAccessLogtype(logType);
//        //描述
//        operationLog.setAccessDesc(desc);
//        //访问数据
//        operationLog.setAccessData(params);
//        operationLog.setCrBy(loginId);
//        operationLog.setCrAt(new Date());
//        operationLog.setUpBy(loginId);
//        operationLog.setUpAt(new Date());
//        operationLogService.insertAutoKey(operationLog);
//    }

    @Around("annotationPoinCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        OperationLog operationLog = new OperationLog();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = (String) request.getAttribute("token");
        //action路径
        String uri = request.getRequestURI();
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME + token);
        if (loginUser != null) {
            //用户id
            operationLog.setSysUserId(loginUser.getId());
            operationLog.setCrBy(loginUser.getId());
            operationLog.setUpBy(loginUser.getId());
        }
        LogDog logDog = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(LogDog.class);
        /*result为连接点的放回结果*/
        Object result = null;
        /*前置通知方法*/
        //访问数据
        operationLog.setAccessData(new JSONArray(proceedingJoinPoint.getArgs()).toString());
        //访问功能
        operationLog.setAccessFunction(proceedingJoinPoint.getSignature().getName());
        //访问来源
        operationLog.setAccessSource(request.getRemoteAddr());
        //访问路径
        operationLog.setAccessPath(uri);
        //访问时间
        operationLog.setAccessTime(new Date());
        //访问类型
        operationLog.setAccessType(logDog.reqSource().getLabel());
        //访问日志类型
        operationLog.setAccessLogtype(logDog.logType().getLabel());
        //描述
        operationLog.setAccessDesc(logDog.desc());
        /*执行目标方法*/
        try {
            result = proceedingJoinPoint.proceed();
            JSONObject jsonObject = JSONUtil.parseObj(result);
            operationLog.setAccessResult(jsonObject.toString());
            /*返回通知方法*/
//            System.out.println("返回通知方法>目标方法名" + methodName + ",返回结果为：" + result);
        } catch (Throwable e) {
            /*异常通知方法*/
//            System.out.println("异常通知方法>目标方法名" + methodName + ",异常为：" + e);
            operationLog.setAccessException(e.getMessage());
            throw new Exception(e);
        }finally {
            operationLog.setCrAt(new Date());
            operationLog.setUpAt(new Date());
            operationLogService.insertAutoKey(operationLog);
        }
        /*后置通知*/
//        System.out.println("后置通知方法>目标方法名" + methodName);
        return result;
    }

}
