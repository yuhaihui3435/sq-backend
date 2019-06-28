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
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.RoleResService;
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
@RequestMapping("/roleRes")
@Slf4j
public class RoleResController extends BaseController {

    @Autowired
    private RoleResService roleResService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody RoleRes condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = roleResService.page("roleRes.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param roleRes
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody RoleRes roleRes) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(roleRes);
        roleRes.setCrBy(currLoginUser().getId());
        roleResService.insertAutoKey(roleRes);
        respBody.setMsg("新增角色资源关系成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param roleRes
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody RoleRes roleRes) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(roleRes);
        roleRes.setUpBy(currLoginUser().getId());
        roleResService.updateTplById(roleRes);
        respBody.setMsg("更新角色资源关系成功");
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
            RoleRes roleRes = roleResService.one(Long.parseLong(id));
            roleRes.setDeAt(new Date());
            roleRes.setDeBy(currLoginUser().getId());
            roleResService.updateTplById(roleRes);
        }
        respBody.setMsg("删除角色资源关系成功");
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
    public RoleRes view(@PathVariable("id") Long id) {
        RoleRes roleRes = roleResService.one("roleRes.sample", RoleRes.builder().id(id).build());
        return roleRes;
    }

    /**
     * 设置有效时长
     *
     * @param map :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/resTimeSet")
    public RespBody resTimeSet(@RequestBody Map<String, RoleRes[]> map) {
        RespBody respBody = new RespBody();
        RoleRes[] roleMenus = map.get("roleRess");
        for (int i = 0; i < roleMenus.length; i++) {
            List<RoleRes> list = roleResService.listByRoleIdAndResId(roleMenus[i].getSysRoleId(), roleMenus[i].getSysResId());
            if (list.size() > 0) {
                RoleRes roleRes = list.get(0);
                roleRes.setEffect(roleMenus[i].getEffect());
                roleRes.setExpired(roleMenus[i].getExpired());
                roleResService.update(roleRes);
            }
        }
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

}
