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
import com.neuray.wp.entity.RoleUser;
import com.neuray.wp.entity.RoleWidget;
import com.neuray.wp.entity.UserWidget;
import com.neuray.wp.entity.Widget;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.model.UserWidgetDto;
import com.neuray.wp.service.UserWidgetService;
import com.neuray.wp.service.WidgetService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userWidget")
@Slf4j
public class

UserWidgetController extends BaseController {

    @Autowired
    private UserWidgetService userWidgetService;
    @Autowired
    private WidgetService widgetService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody UserWidget condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = userWidgetService.page("userWidget.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param userWidget
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody UserWidget userWidget) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userWidget);
        userWidget.setCrBy(currLoginUser().getId());
        userWidgetService.insertAutoKey(userWidget);
        respBody.setMsg("新增用户小组件关系成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param userWidget
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody UserWidget userWidget) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userWidget);
        userWidget.setUpBy(currLoginUser().getId());
        userWidgetService.updateTplById(userWidget);
        respBody.setMsg("更新用户小组件关系成功");
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
            UserWidget userWidget = userWidgetService.one(Long.parseLong(id));
            userWidget.setDeAt(new Date());
            userWidget.setDeBy(currLoginUser().getId());
            userWidgetService.updateTplById(userWidget);
        }
        respBody.setMsg("删除用户小组件关系成功");
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
    public UserWidget view(@PathVariable("id") Long id) {
        UserWidget userWidget = userWidgetService.one("userWidget.sample", UserWidget.builder().id(id).build());
        return userWidget;
    }

    /**
     * 改变小组件位置
     *
     * @param param :
     * @return : com.neuray.wp.core.RespBody
     */
//    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/layoutWidget")
    public RespBody layoutWidget(@RequestBody Map<String, List<UserWidgetDto>> param) {
        RespBody respBody = new RespBody();
        List<UserWidgetDto> list = param.get("newLayout");
        for (int i = 0; i < list.size(); i++) {
            UserWidgetDto userWidgetDto = list.get(i);
            UserWidget userWidget = userWidgetService.queryUserWidgetLinkByCode(userWidgetDto.getI(), currLoginUser().getId()).get(0);
            userWidget.setDftW(userWidgetDto.getW());
            userWidget.setDefH(userWidgetDto.getH());
            userWidget.setPointX(userWidgetDto.getX());
            userWidget.setPointY(userWidgetDto.getY());
            userWidgetService.update(userWidget);
        }
        return respBody;
    }

    /**
     * 根据用户id查询小组件id
     *
     * @param param :
     * @return : java.lang.Long[]
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryWidgetByUser")
    public Long[] queryWidgetByUser(@RequestBody Map<String, String> param) {
        List<Widget> list = new ArrayList<Widget>();
        list = widgetService.queryWidgetByUser(param.get("userId"));
        Long[] l = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getId();
        }
        return l;
    }

    /**
     * 配置用户组件
     *
     * @param param :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/settingWidget")
    public RespBody settingWidget(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据用户id查询出所有的用户小组件关系数据，全部删除，再重新添加本次的数据
        List<UserWidget> userWidgetList = userWidgetService.queryUserWidgetByUserId(Long.parseLong(param.get("userId")));
        for (int i = 0; i < userWidgetList.size(); i++) {
            UserWidget userWidget = userWidgetList.get(i);
            userWidget.setDeAt(new Date());
            userWidget.setDeBy(currLoginUser().getId());
            userWidgetService.update(userWidget);
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (int i = 0; i < idArray.length; i++) {
            String userId = param.get("userId");
            //添加
            UserWidget userWidget = new UserWidget();
            userWidget.setSysUserId(Long.parseLong(userId));
            userWidget.setWidgetId(Long.parseLong(idArray[i]));
            userWidget.setCrBy(currLoginUser().getId());
            userWidget.setUpBy(currLoginUser().getId());
            userWidget.setEffect(new Date());
            userWidget.setPointX(0L);
            userWidget.setPointY(0L);
            userWidget.setDftW(12L);
            userWidget.setDefH(8L);
            userWidgetService.insertAutoKey(userWidget);
        }
        respBody.setMsg("操作成功");
        respBody.setCode(RespBody.SUCCESS);
        return respBody;
    }

    /**
     * 根据用户id查询小组件对象
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryUserWidgetByUser")
    public List<UserWidget> queryUserWidgetByUser(@RequestBody Map<String, String> param) {
        return userWidgetService.queryUserWidgetByUser(param.get("userId"));
    }

    /**
     * 用户小组件时效配置
     *
     * @param map :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/widgetTimeSet")
    public RespBody widgetTimeSet(@RequestBody Map<String, UserWidget[]> map) {
        RespBody respBody = new RespBody();
        UserWidget[] userWidgets = map.get("userWidgets");
        for (int i = 0; i < userWidgets.length; i++) {
            List<UserWidget> list = userWidgetService.listByUserIdAndWidgetId(userWidgets[i].getSysUserId(), userWidgets[i].getWidgetId());
            if (list.size() > 0) {
                UserWidget userWidget = list.get(0);
                userWidget.setEffect(userWidgets[i].getEffect());
                userWidget.setExpired(userWidgets[i].getExpired());
                userWidgetService.update(userWidget);
            }
        }
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

}
