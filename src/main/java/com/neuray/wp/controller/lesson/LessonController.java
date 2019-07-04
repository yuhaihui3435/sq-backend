////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.lesson;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.lesson.Lesson;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.lesson.LessonService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/lesson")
@Slf4j
public class LessonController extends BaseController {

    @Autowired
    private LessonService lessonService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Lesson> query(@RequestBody Lesson condition) {
            return lessonService.many("lesson.sample", condition);
        }
    /**
     *
     * 分页
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
        pageQuery = lessonService.page("lesson.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param lesson
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Lesson lesson) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(lesson);
        lesson.setCrBy(currLoginUser().getId());
        lesson.setUpBy(currLoginUser().getId());
        lessonService.insertAutoKey(lesson);
        respBody.setMsg("新增课程成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param lesson
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Lesson lesson) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(lesson);
        lesson.setUpBy(currLoginUser().getId());
        lessonService.update(lesson);
        respBody.setMsg("更新课程成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String,String> param) {
        RespBody respBody = new RespBody();
        if(StrUtil.isBlank(param.get("ids"))){throw new LogicException("删除操作失败，缺少删除数据");}
        String[] idArray=StrUtil.split(param.get("ids"),",");
        for(String id:idArray){
            Lesson lesson=lessonService.one(Long.parseLong(id));
            lesson.setDeAt(new Date());
            lesson.setDeBy(currLoginUser().getId());
            lessonService.updateTplById(lesson);
        }
        respBody.setMsg("删除课程成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Lesson view(@PathVariable("id") Long id) {
        Lesson lesson=lessonService.one("lesson.sample",Lesson.builder().id(id).build());
        return lesson;
    }

}
