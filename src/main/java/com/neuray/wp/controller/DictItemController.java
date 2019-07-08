////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Dict;
import com.neuray.wp.entity.DictItem;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.CacheService;
import com.neuray.wp.service.DictItemService;
import com.neuray.wp.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dictItem")
@Slf4j
public class DictItemController extends BaseController {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private CacheService cacheService;

    /**
     * @return java.util.List<com.neuray.wp.entity.DictItem>
     * @Description 条件查询
     * @Param [condition]
     * @Author zzq
     * @Date 2019/7/8 10:09
     **/
    @RequestMapping("/query")
    public List<DictItem> query(@RequestBody DictItem condition) {
        return dictItemService.many("dictItem.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody DictItem condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
//        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = dictItemService.page("dictItem.special", pageQuery);
        return pageQuery;
    }

    /**
     * 查询组件类型
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.DictItem>
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/listByType")
    public List<DictItem> listByType(@RequestBody Map<String, String> param) {
        String dictVal = param.get("dictVal");
        Dict dict = dictService.tplOne(Dict.builder().dictVal(dictVal).build());
        DictItem dictItem = DictItem.builder().dictId(dict.getId()).build();
        return dictItemService.tpl(dictItem);
    }

    /**
     * 新增
     *
     * @param dictItem
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody DictItem dictItem) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(dictItem);
        List<DictItem> list = dictItemService.checkItemName(dictItem.getItemName(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("条目名称已存在");
            return respBody;
        }
        list = dictItemService.checkItemVal(dictItem.getItemVal(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("条目值已存在");
            return respBody;
        }
        dictItem.setCrBy(currLoginUser().getId());
        dictItem.setUpBy(currLoginUser().getId());
        dictItemService.insertAutoKey(dictItem);
        cacheService.refreshDictCache();
        respBody.setMsg("新增字典条目成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param dictItem
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody DictItem dictItem) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(dictItem);
        List<DictItem> list = dictItemService.checkItemName(dictItem.getItemName(), dictItem.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("条目名称已存在");
            return respBody;
        }
        list = dictItemService.checkItemVal(dictItem.getItemVal(), dictItem.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("条目值已存在");
            return respBody;
        }
        dictItem.setUpBy(currLoginUser().getId());
        dictItemService.updateTplById(dictItem);
        cacheService.refreshDictCache();
        respBody.setMsg("更新字典条目成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.LOGICDEL, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            DictItem dict = dictItemService.one(Long.parseLong(id));
            dict.setDeAt(new Date());
            dict.setDeBy(currLoginUser().getId());
            dictItemService.updateTplById(dict);
        }
        cacheService.refreshDictCache();
        respBody.setMsg("删除数据字典成功");
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
    public DictItem view(@PathVariable("id") Long id) {
        DictItem dictItem = dictItemService.one("dictItem.sample", DictItem.builder().id(id).build());
        return dictItem;
    }

}
