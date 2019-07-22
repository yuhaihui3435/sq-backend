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
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 课程
 * @Param
 * @Author zzq
 * @Date 2019/7/21 15:37
 * @return
 **/
@RestController
@RequestMapping("/api/lesson")
public class ApiLessonController {

    @Autowired
    private LessonService lessonService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Lesson condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = lessonService.page("lesson.lesson.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 详细
     *
     * @return
     */
    @PostMapping("/view")
    public Lesson view(@RequestBody Lesson condition) {
        Lesson lesson = lessonService.one("lesson.lesson.sample", condition);
        return lesson;
    }

}
