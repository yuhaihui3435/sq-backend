////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.SysMenuRightJson;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.SysMenuRightService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/sysMenuRight")
@Slf4j
public class SysMenuRightController extends BaseController {

    @Autowired
    private SysMenuRightService sysMenuRightService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public Map page(@RequestBody SysMenuRight condition) throws Exception {
        Map map = new HashMap();
        SysMenuRightJson treeJson = new SysMenuRightJson();
        treeJson.setSmName("系统菜单");
        treeJson.setId(-1L);
        List<SysMenuRight> listDept = sysMenuRightService.listAll();
        List<SysMenuRightJson> listChild = new ArrayList<SysMenuRightJson>();
        for (int i = 0; i < listDept.size(); i++) {
            SysMenuRight sysMenuRight = listDept.get(i);
            SysMenuRightJson treeJson1 = new SysMenuRightJson();
            treeJson1.setId(sysMenuRight.getId());
            treeJson1.setSmName(sysMenuRight.getSmName());
            treeJson1.setSmCode(sysMenuRight.getSmCode());
            treeJson1.setSmIcon(sysMenuRight.getSmIcon());
            treeJson1.setSmUri(sysMenuRight.getSmUri());
            treeJson1.setQn(sysMenuRight.getQn());
            treeJson1.setDisplay(sysMenuRight.getDisplay());
            treeJson1.setParentId(sysMenuRight.getParentId());
            treeJson1.setCrBy(sysMenuRight.getCrBy());
            treeJson1.setCrAt(sysMenuRight.getCrAt());
            treeJson1.setUpBy(sysMenuRight.getUpBy());
            treeJson1.setUpAt(sysMenuRight.getUpAt());
            treeJson1.setDeBy(sysMenuRight.getDeBy());
            treeJson1.setDeAt(sysMenuRight.getDeAt());
            treeJson1.setStatus(sysMenuRight.getStatus());
            listChild.add(treeJson1);
        }
        List<SysMenuRightJson> listResult = sysMenuRightService.getTreeOrgs(listChild);
        treeJson.setListChild(listResult);
        String jsonStr = JSONUtil.toJsonStr(treeJson);
        JSONArray jsonArray = JSONUtil.parseArray("[" + jsonStr + "]");
        log.info(jsonArray.toString());
        map.put("jsonArray", jsonArray);
        return map;
    }

    /**
     * 查询菜单右侧列表
     *
     * @param condition :
     * @return : org.beetl.sql.core.engine.PageQuery
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/pageItem")
    public PageQuery pageItem(@RequestBody SysMenuRight condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setParas(condition);
        pageQuery = sysMenuRightService.page("sysMenuRight.special", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param sysMenuRight
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody SysMenuRight sysMenuRight) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(sysMenuRight);
        List<SysMenuRight> list = sysMenuRightService.checkSmCode(sysMenuRight.getSmCode(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("菜单代码已存在");
            return respBody;
        }
        sysMenuRight.setCrBy(currLoginUser().getId());
        sysMenuRight.setUpBy(currLoginUser().getId());
        sysMenuRightService.insertAutoKey(sysMenuRight);
        respBody.setMsg("新增系统菜单成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param sysMenuRight
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody SysMenuRight sysMenuRight) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(sysMenuRight);
        List<SysMenuRight> list = sysMenuRightService.checkSmCode(sysMenuRight.getSmCode(), sysMenuRight.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("菜单代码已存在");
            return respBody;
        }
        sysMenuRight.setUpBy(currLoginUser().getId());
        sysMenuRightService.update(sysMenuRight);
        respBody.setMsg("更新系统菜单成功");
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
            SysMenuRight sysMenuRight = sysMenuRightService.one(Long.parseLong(id));
            sysMenuRight.setDeAt(new Date());
            sysMenuRight.setDeBy(currLoginUser().getId());
            sysMenuRightService.updateTplById(sysMenuRight);
        }
        respBody.setMsg("删除系统菜单成功");
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
    public SysMenuRight view(@PathVariable("id") Long id) {
        SysMenuRight sysMenuRight = sysMenuRightService.one("sysMenuRight.sample", SysMenuRight.builder().id(id).build());
        return sysMenuRight;
    }

}
