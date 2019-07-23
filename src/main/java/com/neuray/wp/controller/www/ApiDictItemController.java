////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import com.neuray.wp.entity.DictItem;
import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.entity.lesson.Lesson;
import com.neuray.wp.service.DictItemService;
import com.neuray.wp.service.doctor.DoctorService;
import com.neuray.wp.service.lesson.LessonService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 医生
 * @Param
 * @Author zzq
 * @Date 2019/7/21 15:37
 * @return
 **/
@RestController
@RequestMapping("/api/dictItem")
public class ApiDictItemController {

    @Autowired
    private DictItemService dictItemService;

    /**
     * @return java.util.List<com.neuray.wp.entity.DictItem>
     * @Description 条件查询
     * @Param [condition]
     * @Author zzq
     * @Date 2019/7/8 10:09
     **/
    @RequestMapping("/query")
    public List<DictItem> query(@RequestBody DictItem condition) {
        return dictItemService.many("dictItem.sample", condition);
    }

}
