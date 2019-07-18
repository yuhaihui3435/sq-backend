////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import com.neuray.wp.Consts;
import com.neuray.wp.entity.artice.Artice;
import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.entity.lesson.Lesson;
import com.neuray.wp.entity.website.Carousel;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.artice.ArticeService;
import com.neuray.wp.service.artice.ColumnService;
import com.neuray.wp.service.doctor.DoctorService;
import com.neuray.wp.service.lesson.LessonService;
import com.neuray.wp.service.website.CarouselService;
import com.neuray.wp.service.website.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private LessonService lessonService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ArticeService articeService;
    @Autowired
    private LinksService linksService;

    /**
     * 首页Index
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/index")
    @ResponseBody
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>();
        //读取导航栏菜单
//        List<Column> columns = columnService.many("artice.column.topLevelAllData", new Column());
//        columnService.recursive(columns);
//        ret.put("columnList",columns);
        //读取每日名言
//        SysConf sysConf=(SysConf) redisCacheService.findHVal(RedisCacheService.SYS_CONF_CACHE_NAME,RedisCacheService.SYS_CONF_CACHE_NAME+ Consts.SYS_CONF_AUTHOR);
//        ret.put(Consts.SYS_CONF_AUTHOR,sysConf.getScVal());
//        sysConf=(SysConf) redisCacheService.findHVal(RedisCacheService.SYS_CONF_CACHE_NAME,RedisCacheService.SYS_CONF_CACHE_NAME+ Consts.SYS_CONF_CONTENT);
//        ret.put(Consts.SYS_CONF_CONTENT,sysConf.getScVal());
        //读取轮播数据
        List<Carousel> carousels = carouselService.many("website.carousel.sample", Carousel.builder().status(Consts.STATUS.AVAILABLE.getCode()).build());
        ret.put("carouselList", carousels);

        return ret;
    }

    /**
     * 读取首页关于课程的数据
     * @param lSize 左侧数据数量
     * @param rSize 右侧数据长度
     * @return
     */
    @PostMapping("/index01")
    @ResponseBody
    public Object index01(@RequestParam(defaultValue = "3") int lSize, @RequestParam(defaultValue = "3") int rSize) {
        Map<String, Object> ret = new HashMap<>();
        List<Lesson> lessons = lessonService.findByIndexShow(lSize);
        ret.put("lessonLData", lessons);
        lessons = lessonService.findByNotIndexShow(rSize);
        ret.put("lessonRData", lessons);
        return ret;
    }

    /**
     * 读取首页关于课程的数据
     * @param lSize
     * @return
     */
    @PostMapping("/index02")
    @ResponseBody
    public Object index02(@RequestParam(defaultValue = "3") int lSize) {
        Map<String, Object> ret = new HashMap<>();
        List<Doctor> doctors = doctorService.findByIndexShow(lSize);
        ret.put("doctorLData", doctors);
        return ret;
    }

    /**
     * 查询首页文章的数据
     * @param size
     * @return
     */
    @PostMapping("/index03")
    @ResponseBody
    public Object index03(@RequestParam(defaultValue = "6") int size,@RequestParam long columnId) {
        Map<String, Object> ret = new HashMap<>();
        List<Artice> artices = articeService.findByTopShow(size,columnId);
        ret.put("articeData", artices);
        return ret;
    }

    /**
     * 查询首页荣誉证书的数据
     * @param size
     * @return
     */
    @PostMapping("/index04")
    @ResponseBody
    public Object index04(@RequestParam(defaultValue = "6") int size) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("certificateData",linksService.findBySize(size,Consts.LINKS_TYPE.certificate.name()));
        return ret;
    }

    /**
     * 查询首页友情链接的数据
     * @param size
     * @return
     */
    @PostMapping("/index05")
    @ResponseBody
    public Object index05(@RequestParam(defaultValue = "6") int size) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("linksData",linksService.findBySize(size,Consts.LINKS_TYPE.link.name()));
        return ret;
    }


}
