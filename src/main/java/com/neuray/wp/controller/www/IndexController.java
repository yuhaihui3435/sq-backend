////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import com.neuray.wp.Consts;
import com.neuray.wp.entity.SysConf;
import com.neuray.wp.entity.artice.Column;
import com.neuray.wp.entity.website.Carousel;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.artice.ColumnService;
import com.neuray.wp.service.website.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:小听风 创建时间:2019/6/29
 * 版本:v1.0
 * @Description:
 */
@Controller
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private ColumnService columnService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private CarouselService carouselService;

    /**
     * 首页Index
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/index")
    @ResponseBody
    public Object index(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> ret=new HashMap<>();
       //读取导航栏菜单
        List<Column> columns = columnService.many("artice.column.topLevelAllData", new Column());
        columnService.recursive(columns);
        ret.put("columnList",columns);
       //读取每日名言
        SysConf sysConf=(SysConf) redisCacheService.findHVal(RedisCacheService.SYS_CONF_CACHE_NAME,RedisCacheService.SYS_CONF_CACHE_NAME+ Consts.SYS_CONF_AUTHOR);
        ret.put(Consts.SYS_CONF_AUTHOR,sysConf.getScVal());
        sysConf=(SysConf) redisCacheService.findHVal(RedisCacheService.SYS_CONF_CACHE_NAME,RedisCacheService.SYS_CONF_CACHE_NAME+ Consts.SYS_CONF_CONTENT);
        ret.put(Consts.SYS_CONF_CONTENT,sysConf.getScVal());
       //读取轮播数据
        List<Carousel> carousels=carouselService.many("website.carousel.sample",Carousel.builder().status(Consts.STATUS.AVAILABLE.getCode()).build());
        ret.put("carouselList",carousels);

        return ret;
    }


}
