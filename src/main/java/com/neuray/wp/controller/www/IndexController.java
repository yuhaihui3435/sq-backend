////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:小听风 创建时间:2019/6/29
 * 版本:v1.0
 * @Description:
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * 首页Index
     * @param request
     * @param response
     * @return
     */
    public String index(HttpServletRequest request, HttpServletResponse response){

       //读取导航栏菜单

       //读取每日名言

       //读取轮播数据



        return "/index";
    }


}
