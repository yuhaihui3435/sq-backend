package com.neuray.wp.interceptor;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.controller.LoginController;
import com.neuray.wp.model.LoginUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 登录检查拦截
 * 时间 2019/2/15
 *
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Component
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {


    @Value("${anonymous.access.paths}")
    private String anonymousAccessPaths;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        String curReqUri=request.getRequestURI();
        List<String> stringList = StrUtil.split(anonymousAccessPaths, StrUtil.C_COMMA);
        boolean isPathIgnored = stringList.stream().anyMatch(p -> {
            return ReUtil.isMatch(p, curReqUri);
        });
        if (isPathIgnored) {
            return true;
        }
        String token = request.getHeader("token");
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME + token);
        if (loginUser == null) {
            response.setStatus(401);
            return false;
        } else {
            request.setAttribute("token", token);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
