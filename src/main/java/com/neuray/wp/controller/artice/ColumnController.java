////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.artice;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.artice.Column;
import com.neuray.wp.entity.artice.ColumnTag;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.artice.ColumnService;
import com.neuray.wp.service.artice.ColumnTagService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/column")
@Slf4j
public class ColumnController extends BaseController {

    @Autowired
    private ColumnService columnService;
    @Autowired
    private ColumnTagService columnTagService;
    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @PostMapping("/query")
    public List<Column> query(@RequestBody Column condition) {
        return columnService.many("artice.column.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Column condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = columnService.page("artice.column.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param column
     * @return
     */
    @PostMapping("/save")
    @Transactional
    public RespBody save(@RequestBody Column column) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(column);
        List<Column> list = columnService.checkName(column.getName(), null);
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("已存在");
            return respBody;
        }
        column.setCrBy(currLoginUser().getId());
        column.setUpBy(currLoginUser().getId());
        columnService.insertAutoKey(column);
        if(column.getTags()!=null){
            column.getTags().stream().forEach(dictItem -> {
                columnTagService.insertAutoKey(ColumnTag.builder().columnId(column.getId()).tagId(dictItem.getId()).build());
            });
        }
        respBody.setMsg("新增栏目成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param column
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Column column) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(column);
        List<Column> list = columnService.checkName(column.getName(), column.getId());
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("已存在");
            return respBody;
        }
        columnTagService.many("artice.columnTag.sample",ColumnTag.builder().columnId(column.getId()).build()).forEach(
                columnTag -> {
                    columnTagService.del(columnTag.getId());
                }
        );

        if(column.getTags()!=null){
            column.getTags().stream().forEach(dictItem -> {
                columnTagService.insertAutoKey(ColumnTag.builder().columnId(column.getId()).tagId(dictItem.getId()).build());
            });
        }
        column.setUpBy(currLoginUser().getId());
        columnService.update(column);
        respBody.setMsg("更新栏目成功");
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
        if (StrUtil.isBlank(param.get("ids"))) {
            throw new LogicException("删除操作失败，缺少删除数据");
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            Column column = columnService.one(Long.parseLong(id));
            column.setDeAt(new Date());
            column.setDeBy(currLoginUser().getId());
            columnService.updateTplById(column);
        }
        respBody.setMsg("删除栏目成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Column view(@PathVariable("id") Long id) {
        Column column = columnService.one("artice.column.sample", Column.builder().id(id).build());
        return column;
    }

    /**
     * 顶级全数据查询，为树形渲染使用
     *
     * @return
     */
    @GetMapping("/topLevelAllData")
    public List<Column> topLevelAllData() {
        List<Column> columns = columnService.many("artice.column.topLevelAllData", new Column());
        columnService.recursive(columns);
        return columns;
    }



}
