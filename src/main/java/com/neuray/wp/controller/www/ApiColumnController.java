////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import com.neuray.wp.entity.artice.Column;
import com.neuray.wp.entity.lesson.Lesson;
import com.neuray.wp.service.artice.ColumnService;
import com.neuray.wp.service.lesson.LessonService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 栏目
 * @Param
 * @Author zzq
 * @Date 2019/7/21 15:37
 * @return
 **/
@RestController
@RequestMapping("/api/column")
public class ApiColumnController {

    @Autowired
    private ColumnService columnService;

    /**
     * 顶级全数据查询，为树形渲染使用
     *
     * @return
     */
    @RequestMapping("/topLevelAllData")
    public List<Column> topLevelAllData() {
        List<Column> columns = columnService.many("artice.column.topLevelAllData", new Column());
        columnService.recursive(columns);
        return columns;
    }

}
