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
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.SysMenuRightJson;
import com.neuray.wp.entity.SysResRight;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.entity.SysResRightJson;
import com.neuray.wp.service.SysResRightService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/sysResRight")
@Slf4j
public class SysResRightController extends BaseController {

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
    public Map page(@RequestBody SysResRight condition) throws Exception {
        Map map = new HashMap();
        SysResRightJson treeJson = new SysResRightJson();
        treeJson.setResName("系统资源");
        treeJson.setId(-1L);
        List<SysResRight> listRes = sysResRightService.listAll();
        List<SysResRightJson> listChild = new ArrayList<SysResRightJson>();
        for (int i = 0; i < listRes.size(); i++) {
            SysResRight sysResRight = listRes.get(i);
            SysResRightJson treeJson1 = new SysResRightJson();
            treeJson1.setId(sysResRight.getId());
            treeJson1.setResName(sysResRight.getResName());
            treeJson1.setCode(sysResRight.getCode());
            treeJson1.setResUri(sysResRight.getResUri());
            treeJson1.setType(sysResRight.getType());
            treeJson1.setParentId(sysResRight.getParentId());
            treeJson1.setCrBy(sysResRight.getCrBy());
            treeJson1.setCrAt(sysResRight.getCrAt());
            treeJson1.setUpBy(sysResRight.getUpBy());
            treeJson1.setUpAt(sysResRight.getUpAt());
            treeJson1.setDeBy(sysResRight.getDeBy());
            treeJson1.setDeAt(sysResRight.getDeAt());
            treeJson1.setStatus(sysResRight.getStatus());
            listChild.add(treeJson1);
        }
        List<SysResRightJson> listResult = sysResRightService.getTreeOrgs(listChild);
        treeJson.setListChild(listResult);
        String jsonStr = JSONUtil.toJsonStr(treeJson);
        JSONArray jsonArray = JSONUtil.parseArray("[" + jsonStr + "]");
        log.info(jsonArray.toString());
        map.put("jsonArray", jsonArray);
        return map;
    }

    /**
     * 查询资源右侧列表
     *
     * @param condition :
     * @return : org.beetl.sql.core.engine.PageQuery
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/pageItem")
    public PageQuery pageItem(@RequestBody SysResRight condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setParas(condition);
        pageQuery = sysResRightService.page("sysResRight.special", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param sysResRight
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody SysResRight sysResRight) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(sysResRight);
        List<SysResRight> list = sysResRightService.checkResName(sysResRight.getResName(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("资源名称已存在");
            return respBody;
        }
        list = sysResRightService.checkCode(sysResRight.getCode(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("资源编码已存在");
            return respBody;
        }
        sysResRight.setCrBy(currLoginUser().getId());
        sysResRight.setUpBy(currLoginUser().getId());
        sysResRightService.insertAutoKey(sysResRight);
        respBody.setMsg("新增系统资源成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param sysResRight
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody SysResRight sysResRight) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(sysResRight);
        List<SysResRight> list = sysResRightService.checkResName(sysResRight.getResName(), sysResRight.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("资源名称已存在");
            return respBody;
        }
        list = sysResRightService.checkCode(sysResRight.getCode(), sysResRight.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("资源编码已存在");
            return respBody;
        }
        sysResRight.setUpBy(currLoginUser().getId());
        sysResRightService.updateTplById(sysResRight);
        respBody.setMsg("更新系统资源成功");
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
            SysResRight sysResRight = sysResRightService.one(Long.parseLong(id));
            sysResRight.setDeAt(new Date());
            sysResRight.setDeBy(currLoginUser().getId());
            sysResRightService.updateTplById(sysResRight);
        }
        respBody.setMsg("删除系统资源成功");
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
    public SysResRight view(@PathVariable("id") Long id) {
        SysResRight sysResRight = sysResRightService.one("sysResRight.sample", SysResRight.builder().id(id).build());
        return sysResRight;
    }

}
