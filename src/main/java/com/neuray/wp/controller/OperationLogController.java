////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.OperationLog;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.OperationLogService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/operationLog")
@Slf4j
public class OperationLogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody OperationLog condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = operationLogService.page("operationLog.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param operationLog
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody OperationLog operationLog) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(operationLog);
        operationLog.setCrBy(currLoginUser().getId());
        operationLogService.insertAutoKey(operationLog);
        respBody.setMsg("新增操作日志成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param operationLog
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody OperationLog operationLog) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(operationLog);
        operationLog.setUpBy(currLoginUser().getId());
        operationLogService.updateTplById(operationLog);
        respBody.setMsg("更新操作日志成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) throw new LogicException("删除操作失败，缺少删除数据");
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            OperationLog operationLog = operationLogService.one(Long.parseLong(id));
            operationLog.setDeAt(new Date());
            operationLog.setDeBy(currLoginUser().getId());
            operationLogService.updateTplById(operationLog);
        }
        respBody.setMsg("删除操作日志成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public OperationLog view(@PathVariable("id") Long id) {
        OperationLog operationLog = operationLogService.one("operationLog.sample", OperationLog.builder().id(id).build());
        return operationLog;
    }

    /**
     * 查询访问数据
     *
     * @param id :
     * @return : java.lang.String
     */
    @PostMapping("/queryAccessData/{id}")
    public OperationLog queryAccessData(@PathVariable("id") Long id) {
        OperationLog operationLog = operationLogService.one("operationLog.queryAccessData", OperationLog.builder().id(id).build());
        return operationLog;
    }

    /**
     * 查询描述
     *
     * @param id :
     * @return : com.neuray.wp.entity.OperationLog
     */
    @PostMapping("/queryAccessDesc/{id}")
    public OperationLog queryAccessDesc(@PathVariable("id") Long id) {
        OperationLog operationLog = operationLogService.one("operationLog.queryAccessDesc", OperationLog.builder().id(id).build());
        return operationLog;
    }

    /**
     * 查询返回结果
     *
     * @param id :
     * @return : com.neuray.wp.entity.OperationLog
     */
    @PostMapping("/queryAccessResult/{id}")
    public OperationLog queryAccessResult(@PathVariable("id") Long id) {
        OperationLog operationLog = operationLogService.one("operationLog.queryAccessResult", OperationLog.builder().id(id).build());
        return operationLog;
    }

    /**
     * 查询异常
     *
     * @param id :
     * @return : com.neuray.wp.entity.OperationLog
     */
    @PostMapping("/queryAccessException/{id}")
    public OperationLog queryAccessException(@PathVariable("id") Long id) {
        OperationLog operationLog = operationLogService.one("operationLog.queryAccessException", OperationLog.builder().id(id).build());
        return operationLog;
    }

    /**
     * 查询访问日志类型和访问类型
     *
     * @return : java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    @PostMapping("queryOperationType")
    public Map<String, List<String>> queryOperationType() {
        List<String> listLogType = new ArrayList<String>();
        List<String> listReqSource = new ArrayList<String>();
        listLogType = Arrays.stream(Consts.LOGTYPE.values()).map(str -> {
            return str.getLabel();
        }).collect(Collectors.toList());
        listReqSource = Arrays.stream(Consts.REQSOURCE.values()).map(str -> {
            return str.getLabel();
        }).collect(Collectors.toList());
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.put("listLogType", listLogType);
        map.put("listReqSource", listReqSource);
        return map;
    }

}
