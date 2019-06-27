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
import com.neuray.wp.entity.*;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/sysRole")
@Slf4j
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleResService roleResService;

    @Autowired
    private SysMenuRightService sysMenuRightService;

    @Autowired
    private SysResRightService sysResRightService;

    @Autowired
    private RoleWidgetService roleWidgetService;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private UserWidgetService userWidgetService;

    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/query")
    public List<SysRole> query(@RequestBody SysRole condition) {
        return sysRoleService.many("sysRole.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody SysRole condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = sysRoleService.page("sysRole.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param sysRole
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody SysRole sysRole) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(sysRole);
        List<SysRole> list = sysRoleService.checkRoleCode(sysRole.getRoleCode(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("角色编码已存在");
            return respBody;
        }
        list = sysRoleService.checkRoleName(sysRole.getRoleName(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("角色名称已存在");
            return respBody;
        }
        sysRole.setCrBy(currLoginUser().getId());
        sysRole.setUpBy(currLoginUser().getId());
        sysRoleService.insertAutoKey(sysRole);
        respBody.setMsg("新增系统角色成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param sysRole
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody SysRole sysRole) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(sysRole);
        List<SysRole> list = sysRoleService.checkRoleCode(sysRole.getRoleCode(), sysRole.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("角色编码已存在");
            return respBody;
        }
        list = sysRoleService.checkRoleName(sysRole.getRoleName(), sysRole.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("角色名称已存在");
            return respBody;
        }
        sysRole.setUpBy(currLoginUser().getId());
        sysRoleService.updateTplById(sysRole);
        respBody.setMsg("更新系统角色成功");
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
            SysRole sysRole = sysRoleService.one(Long.parseLong(id));
            sysRole.setDeAt(new Date());
            sysRole.setDeBy(currLoginUser().getId());
            sysRoleService.updateTplById(sysRole);
        }
        respBody.setMsg("删除系统角色成功");
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
    public SysRole view(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.one("sysRole.sample", SysRole.builder().id(id).build());
        return sysRole;
    }

    /**
     * 配置菜单
     *
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/settingMenu")
    public RespBody settingMenu(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据角色id查询出所有的角色菜单关系数据，全部删除，再重新添加本次的数据
//        List<RoleMenu> roleMenuList = roleMenuService.queryMenuByRole(param.get("roleId"));
//        List<RoleMenu> roleMenuList = roleMenuService.tpl(RoleMenu.builder().sysRoleId(Long.parseLong(param.get("roleId"))).build());
        List<RoleMenu> roleMenuList = roleMenuService.queryRoleMenuByRole(Long.parseLong(param.get("roleId")));
        for (int i = 0; i < roleMenuList.size(); i++) {
            RoleMenu roleMenu = roleMenuList.get(i);
            roleMenu.setDeAt(new Date());
            roleMenu.setDeBy(currLoginUser().getId());
            roleMenuService.update(roleMenu);
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (int i = 0; i < idArray.length; i++) {
            String roleId = param.get("roleId");
            //添加
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setSysRoleId(Long.parseLong(roleId));
            roleMenu.setSysMenuId(Long.parseLong(idArray[i]));
            roleMenu.setCrBy(currLoginUser() != null ? currLoginUser().getId() : null);
            roleMenu.setUpBy(currLoginUser() != null ? currLoginUser().getId() : null);
            roleMenu.setEffect(new Date());
            roleMenuService.insertAutoKey(roleMenu);
        }
        respBody.setMsg("操作成功");
        respBody.setCode(RespBody.SUCCESS);
        return respBody;
    }

    /**
     * 配置资源
     *
     * @param param :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/settingRes")
    public RespBody settingRes(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据角色id查询出所有的角色资源关系数据，全部删除，再重新添加本次的数据
//        List<RoleRes> roleResList = roleResService.tpl(RoleRes.builder().sysRoleId(Long.parseLong(param.get("roleId"))).build());
        List<RoleRes> roleResList = roleResService.queryRoleResByRole(Long.parseLong(param.get("roleId")));
        for (int i = 0; i < roleResList.size(); i++) {
            RoleRes roleRes = roleResList.get(i);
            roleRes.setDeAt(new Date());
            roleRes.setDeBy(currLoginUser().getId());
            roleResService.update(roleRes);
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (int i = 0; i < idArray.length; i++) {
            String roleId = param.get("roleId");
            //添加
            RoleRes roleRes = new RoleRes();
            roleRes.setSysRoleId(Long.parseLong(roleId));
            roleRes.setSysResId(Long.parseLong(idArray[i]));
            roleRes.setCrBy(currLoginUser() != null ? currLoginUser().getId() : null);
            roleRes.setUpBy(currLoginUser() != null ? currLoginUser().getId() : null);
            roleRes.setEffect(new Date());
            roleResService.insertAutoKey(roleRes);
        }
        respBody.setMsg("操作成功");
        respBody.setCode(RespBody.SUCCESS);
        return respBody;
    }

    /**
     * 配置组件
     *
     * @param param :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/settingWidget")
    public RespBody settingWidget(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据角色id查询出所有的角色小组件关系数据，全部删除，再重新添加本次的数据
//        List<RoleWidget> roleWidgetList = roleWidgetService.tpl(RoleWidget.builder().id(Long.parseLong(param.get("roleId"))).build());
        List<RoleWidget> roleWidgetList = roleWidgetService.queryRoleWidgetByRole(Long.parseLong(param.get("roleId")));
        for (int i = 0; i < roleWidgetList.size(); i++) {
            RoleWidget roleWidget = roleWidgetList.get(i);
            roleWidget.setDeAt(new Date());
            roleWidget.setDeBy(currLoginUser().getId());
            roleWidgetService.update(roleWidget);
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (int i = 0; i < idArray.length; i++) {
            String roleId = param.get("roleId");
            //添加
            RoleWidget roleWidget = new RoleWidget();
            roleWidget.setRoleId(Long.parseLong(roleId));
            roleWidget.setWidgetId(Long.parseLong(idArray[i]));
            roleWidget.setCrBy(currLoginUser().getId());
            roleWidget.setUpBy(currLoginUser().getId());
            roleWidget.setEffect(new Date());
            roleWidgetService.insertAutoKey(roleWidget);
        }
        //查询此角色所挂的小组件id，再找出此角色所有的用户，再找出这些用户所有通过角色挂的小组件，删除
//        List<RoleUser> roleUserList = roleUserService.tpl(RoleUser.builder().sysRoleId(Long.parseLong(param.get("roleId"))).build());
        List<RoleUser> roleUserList = roleUserService.queryRoleUserBuRole(Long.parseLong(param.get("roleId")));
        for (int i = 0; i < roleUserList.size(); i++) {
            //用每个用户id去查询用户组件关系对象，并全部删除
            RoleUser roleUser = roleUserList.get(i);
            List<UserWidget> userWidgetList = userWidgetService.queryUserWidgetByUserId(roleUser.getSysUserId());//加上类型，根据角色过来的
            for (int j = 0; j < userWidgetList.size(); j++) {
                UserWidget userWidget = userWidgetList.get(j);
                userWidget.setDeAt(new Date());
                userWidget.setDeBy(currLoginUser().getId());
                userWidgetService.update(userWidget);
            }
            //再将此次挂的小组件，添加到用户和小组件关系表中
            for (int j = 0; j < idArray.length; j++) {
                //根据小组件id查询小组件
                Widget widget = widgetService.tpl(Widget.builder().id(Long.parseLong(idArray[j])).build()).get(0);
                UserWidget userWidget = new UserWidget();
                userWidget.setType(0L);
                userWidget.setWidgetId(widget.getId());
                userWidget.setSysUserId(roleUser.getSysUserId());
                userWidget.setDftW(widget.getDftW());
                userWidget.setDefH(widget.getDefH());
                userWidget.setPointX(0L);
                userWidget.setPointY(0L);
                userWidget.setCrAt(new Date());
                userWidget.setCrBy(currLoginUser().getId());
                userWidget.setUpAt(new Date());
                userWidget.setUpBy(currLoginUser().getId());
                userWidget.setEffect(new Date());
                userWidgetService.insertAutoKey(userWidget);
            }
        }
        respBody.setMsg("操作成功");
        respBody.setCode(RespBody.SUCCESS);
        return respBody;
    }

    /**
     * 获取角色拥有菜单id
     *
     * @param param :
     * @return : java.lang.Long[]
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryMenuByRole")
    public Long[] queryMenuByRole(@RequestBody Map<String, String> param) {
        List<SysMenuRight> list = new ArrayList<SysMenuRight>();
        list = sysMenuRightService.queryMenuByRole(param.get("roleId"));
        Long[] l = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getId();
        }
        return l;
    }

    /**
     * 获取角色拥有菜单对象
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryMenuObjByRole")
    public List<RoleMenu> queryMenuObjByRole(@RequestBody Map<String, String> param) {
        return roleMenuService.queryMenuByRole(param.get("roleId"));
    }

    /**
     * 获取角色拥有资源id
     *
     * @param param :
     * @return : java.lang.Long[]
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryResByRole")
    public Long[] queryResByRole(@RequestBody Map<String, String> param) {
        List<SysResRight> list = new ArrayList<SysResRight>();
        list = sysResRightService.queryResByRole(param.get("roleId"));
        Long[] l = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getId();
        }
        return l;
    }

    /**
     * 获取角色拥有资源对象
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.SysResRight>
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryResObjByRole")
    public List<RoleRes> queryResObjByRole(@RequestBody Map<String, String> param) {
        return roleResService.queryResByRole(param.get("roleId"));
    }

    /**
     * 获取角色拥有组件id
     *
     * @param param :
     * @return : java.lang.Long[]
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryWidgetByRole")
    public Long[] queryWidgetByRole(@RequestBody Map<String, String> param) {
        List<Widget> list = new ArrayList<Widget>();
        list = widgetService.queryWidgetByRole(param.get("roleId"));
        Long[] l = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getId();
        }
        return l;
    }

    /**
     * 获取角色拥有组件对象
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.Widget>
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryWidgetObjByRole")
    public List<RoleWidget> queryWidgetObjByRole(@RequestBody Map<String, String> param) {
        return roleWidgetService.queryWidgetObjByRole(param.get("roleId"));
    }

    /**
     * 根据用户id查询用户组件
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryUserWidgetByUserId")
    public List<UserWidget> queryUserWidgetByUserId(@RequestBody Map<String, String> param) {
        return userWidgetService.queryWidgetLinkByUserId(Long.parseLong(param.get("userId")));
    }


}
