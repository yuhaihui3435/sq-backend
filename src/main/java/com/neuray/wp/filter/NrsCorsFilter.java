////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.filter;



import com.neuray.wp.kits.ReqKit;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域请求处理
 * 时间 2018/11/2
 * @author 小听风  
 * @version v1.0
 * @see 
 * @since
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@WebFilter(filterName = "nrsCorsFilter",urlPatterns = "/*")
public class NrsCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if(ReqKit.isAjaxRequest((HttpServletRequest)servletRequest)||((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String origin = ((HttpServletRequest) servletRequest).getHeader("Origin");
            //跨域处理
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,Origin,Accept,token");
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
