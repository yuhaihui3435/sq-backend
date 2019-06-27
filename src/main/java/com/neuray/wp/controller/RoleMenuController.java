////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.RoleMenu;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.model.RoleMenuDto;
import com.neuray.wp.service.RoleMenuService;
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
@RequestMapping("/roleMenu")
@Slf4j
public class RoleMenuController extends BaseController {

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody RoleMenu condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = roleMenuService.page("roleMenu.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param roleMenu
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody RoleMenu roleMenu) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(roleMenu);
        roleMenu.setCrBy(currLoginUser().getId());
        roleMenuService.insertAutoKey(roleMenu);
        respBody.setMsg("新增角色菜单关系成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param roleMenu
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody RoleMenu roleMenu) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(roleMenu);
        roleMenu.setUpBy(currLoginUser().getId());
        roleMenuService.updateTplById(roleMenu);
        respBody.setMsg("更新角色菜单关系成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.LOGICDEL,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) throw new LogicException("删除操作失败，缺少删除数据");
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            RoleMenu roleMenu = roleMenuService.one(Long.parseLong(id));
            roleMenu.setDeAt(new Date());
            roleMenu.setDeBy(currLoginUser().getId());
            roleMenuService.updateTplById(roleMenu);
        }
        respBody.setMsg("删除角色菜单关系成功");
        return respBody;
    }


    /**
     * 设置有效时长
     *
     * @param  :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/menuTimeSet")
    public RespBody menuTimeSet(@RequestBody Map<String, RoleMenu[]> map) {
        RespBody respBody = new RespBody();
        RoleMenu[] roleMenus = map.get("roleMenus");
        for (int i = 0; i < roleMenus.length; i++) {
            List<RoleMenu> list = roleMenuService.listByRoleIdAndMenuId(roleMenus[i].getSysRoleId(),roleMenus[i].getSysMenuId());
            if (list.size()>0) {
                RoleMenu roleMenu = list.get(0);
                roleMenu.setEffect(roleMenus[i].getEffect());
                roleMenu.setExpired(roleMenus[i].getExpired());
                roleMenuService.update(roleMenu);
            }
        }
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

}
