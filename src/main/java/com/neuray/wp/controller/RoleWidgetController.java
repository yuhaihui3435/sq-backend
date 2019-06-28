////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.RoleRes;
import com.neuray.wp.entity.RoleWidget;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.RoleWidgetService;
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
@RequestMapping("/roleWidget")
@Slf4j
public class RoleWidgetController extends BaseController {

    @Autowired
    private RoleWidgetService roleWidgetService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody RoleWidget condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = roleWidgetService.page("roleWidget.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param roleWidget
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody RoleWidget roleWidget) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(roleWidget);
        roleWidget.setCrBy(currLoginUser().getId());
        roleWidgetService.insertAutoKey(roleWidget);
        respBody.setMsg("新增角色小组件关系表成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param roleWidget
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody RoleWidget roleWidget) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(roleWidget);
        roleWidget.setUpBy(currLoginUser().getId());
        roleWidgetService.updateTplById(roleWidget);
        respBody.setMsg("更新角色小组件关系表成功");
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
            RoleWidget roleWidget = roleWidgetService.one(Long.parseLong(id));
            roleWidget.setDeAt(new Date());
            roleWidget.setDeBy(currLoginUser().getId());
            roleWidgetService.updateTplById(roleWidget);
        }
        respBody.setMsg("删除角色小组件关系表成功");
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
    public RoleWidget view(@PathVariable("id") Long id) {
        RoleWidget roleWidget = roleWidgetService.one("roleWidget.sample", RoleWidget.builder().id(id).build());
        return roleWidget;
    }

    /**
     * 设置有效时长
     *
     * @param map :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/widgetTimeSet")
    public RespBody widgetTimeSet(@RequestBody Map<String, RoleWidget[]> map) {
        RespBody respBody = new RespBody();
        RoleWidget[] roleWidgets = map.get("roleWidgets");
        for (int i = 0; i < roleWidgets.length; i++) {
            List<RoleWidget> list = roleWidgetService.listByRoleIdAndWidgetId(roleWidgets[i].getRoleId(), roleWidgets[i].getWidgetId());
            if (list.size() > 0) {
                RoleWidget roleWidget = list.get(0);
                roleWidget.setEffect(roleWidgets[i].getEffect());
                roleWidget.setExpired(roleWidgets[i].getExpired());
                roleWidgetService.update(roleWidget);
            }
        }
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

}
