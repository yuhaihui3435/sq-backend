package com.neuray.wp.interceptor;

import com.neuray.wp.controller.LoginController;
import com.neuray.wp.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 访问控制拦截器
 * 时间 2019/2/15
 *
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Component
@Slf4j
public class AccessControlInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = (String) request.getAttribute("token");
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(LoginController.SYSUSER_LOGIN_CACHE_NAME + token);
        String currResURI = request.getRequestURI();
        AtomicBoolean discharged = new AtomicBoolean(false);
//           Set<SysResRight> sysResRightSet=loginUser.getSysResRights();
//           sysResRightSet.stream().forEach(sysResRight -> {
//               if(sysResRight.getResUri().indexOf(currResURI)>-1){
//                    discharged.set(true);
//               }
//           });
        if (discharged.get()) {
            return true;
        } else {
            response.setStatus(403);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
