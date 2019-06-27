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
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.UserMenu;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.SysMenuRightService;
import com.neuray.wp.service.SysResRightService;
import com.neuray.wp.service.UserMenuService;
import com.neuray.wp.service.UserResService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userMenu")
@Slf4j
public class UserMenuController extends BaseController {

    @Autowired
    private UserMenuService userMenuService;
    @Autowired
    private UserResService userResService;
    @Autowired
    private SysMenuRightService sysMenuRightService;
    @Autowired
    private SysResRightService sysResRightService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody UserMenu condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = userMenuService.page("userMenu.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param userMenu
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody UserMenu userMenu) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userMenu);
        userMenu.setCrBy(currLoginUser().getId());
        userMenuService.insertAutoKey(userMenu);
        respBody.setMsg("新增用户菜单关系成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param userMenu
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody UserMenu userMenu) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userMenu);
        userMenu.setUpBy(currLoginUser().getId());
        userMenuService.updateTplById(userMenu);
        respBody.setMsg("更新用户菜单关系成功");
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
            UserMenu userMenu = userMenuService.one(Long.parseLong(id));
            userMenu.setDeAt(new Date());
            userMenu.setDeBy(currLoginUser().getId());
            userMenuService.updateTplById(userMenu);
        }
        respBody.setMsg("删除用户菜单关系成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public UserMenu view(@PathVariable("id") Long id) {
        UserMenu userMenu = userMenuService.one("userMenu.sample", UserMenu.builder().id(id).build());
        return userMenu;
    }

    /**
     * 根据用户id查询菜单id
     *
     * @param param :
     * @return : java.lang.Long[]
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryMenuByUser")
    public Long[] queryMenuByUser(@RequestBody Map<String, String> param) {
        List<SysMenuRight> list = new ArrayList<SysMenuRight>();
        list = sysMenuRightService.queryMenuByUser(param.get("userId"));
        Long[] l = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getId();
        }
        return l;
    }

    /**
     * 配置用户菜单
     *
     * @param param :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/settingMenu")
    public RespBody settingMenu(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据用户id查询出所有的用户菜单关系数据，全部删除，再重新添加本次的数据
        List<UserMenu> userMenuList = userMenuService.queryMenuByUser(param.get("userId"));
        for (int i = 0; i < userMenuList.size(); i++) {
            UserMenu userMenu = userMenuList.get(i);
            userMenu.setDeAt(new Date());
            userMenu.setDeBy(currLoginUser().getId());
            userMenuService.update(userMenu);
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (int i = 0; i < idArray.length; i++) {
            String userId = param.get("userId");
            //添加
            UserMenu userMenu = new UserMenu();
            userMenu.setSysUserId(Long.parseLong(userId));
            userMenu.setSysMenuId(Long.parseLong(idArray[i]));
            userMenu.setCrBy(currLoginUser() != null ? currLoginUser().getId() : null);
            userMenu.setUpBy(currLoginUser() != null ? currLoginUser().getId() : null);
            userMenu.setEffect(new Date());
            userMenuService.insertAutoKey(userMenu);
        }
        respBody.setMsg("操作成功");
        respBody.setCode(RespBody.SUCCESS);
        return respBody;
    }

    /**
     * 根据用户id查询用户菜单关系对象
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.UserMenu>
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryUserMenuByUser")
    public List<UserMenu> queryUserMenuByUser(@RequestBody Map<String, String> param) {
        return userMenuService.queryMenuByUser(param.get("userId"));
    }

    /**
     * 用户菜单时间配置
     *
     * @param map :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/menuTimeSet")
    public RespBody menuTimeSet(@RequestBody Map<String, UserMenu[]> map) {
        RespBody respBody = new RespBody();
        UserMenu[] userMenus = map.get("userMenus");
        for (int i = 0; i < userMenus.length; i++) {
            List<UserMenu> list = userMenuService.listByUserIdAndMenuId(userMenus[i].getSysUserId(), userMenus[i].getSysMenuId());
            if (list.size() > 0) {
                UserMenu userMenu = list.get(0);
                userMenu.setEffect(userMenus[i].getEffect());
                userMenu.setExpired(userMenus[i].getExpired());
                userMenuService.update(userMenu);
            }
        }
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

}
