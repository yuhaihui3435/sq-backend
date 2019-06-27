package com.neuray.wp.interceptor;

import com.neuray.wp.controller.LoginController;
import com.neuray.wp.kits.AppKit;
import com.neuray.wp.model.LoginUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 登录检查拦截
 * 时间 2019/2/15
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Component
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        String token = request.getHeader("token");
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME + token);
        if (loginUser == null) {
            response.setStatus(401);
            return false;
        } else {
            request.setAttribute("token",token);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
