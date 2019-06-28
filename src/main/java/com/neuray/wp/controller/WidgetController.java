////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Widget;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.WidgetService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/widget")
@Slf4j
public class WidgetController extends BaseController {

    @Autowired
    private WidgetService widgetService;

//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/query")
    public List<Widget> query(@RequestBody Widget condition) {
        return widgetService.many("widget.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody Widget condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = widgetService.page("widget.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param widget
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody Widget widget) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(widget);
        List<Widget> list = widgetService.checkCode(widget.getCode(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("编号已存在");
            return respBody;
        }
        widget.setCrBy(currLoginUser().getId());
        widget.setUpBy(currLoginUser().getId());
        widgetService.insertAutoKey(widget);
        respBody.setMsg("新增小组件成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param widget
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody Widget widget) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(widget);
        List<Widget> list = widgetService.checkCode(widget.getCode(), widget.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("编号已存在");
            return respBody;
        }
        widget.setUpBy(currLoginUser().getId());
        widgetService.update(widget);
        respBody.setMsg("更新小组件成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.LOGICDEL, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) throw new LogicException("删除操作失败，缺少删除数据");
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            Widget widget = widgetService.one(Long.parseLong(id));
            widget.setDeAt(new Date());
            widget.setDeBy(currLoginUser().getId());
            widgetService.updateTplById(widget);
        }
        respBody.setMsg("删除小组件成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public Widget view(@PathVariable("id") Long id) {
        Widget widget = widgetService.one("widget.sample", Widget.builder().id(id).build());
        return widget;
    }

}
