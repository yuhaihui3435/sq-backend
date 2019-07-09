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
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Dict;
import com.neuray.wp.entity.DictJson;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/dict")
@Slf4j
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;
    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 查询全部
     *
     * @return : java.util.List<com.neuray.wp.entity.Dict>
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public Map page() {
        Map map = new HashMap();
        List<Dict> list = new ArrayList<Dict>();
        list = dictService.listAll();
        List<DictJson> listDict = new ArrayList<DictJson>();
        for (int i = 0; i < list.size(); i++) {
            Dict dict = list.get(i);
            DictJson dictJson = new DictJson();
            dictJson.setId(dict.getId());
            dictJson.setDictVal(dict.getDictVal());
            dictJson.setDictName(dict.getDictName());
            listDict.add(dictJson);
        }
        DictJson jsonObject = new DictJson();
        jsonObject.setId(-1L);
        jsonObject.setDictVal("-1");
        jsonObject.setDictName("数据字典");
        jsonObject.setListDict(listDict);
        String jsonStr = JSONUtil.toJsonStr(jsonObject);
        JSONArray jsonArray = JSONUtil.parseArray("[" + jsonStr + "]");
        log.info(jsonArray.toString());
        map.put("jsonArray", jsonArray);
        return map;
    }

    /**
     * 新增
     *
     * @param dict
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody Dict dict, HttpServletRequest request) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(dict);
        List<Dict> list = dictService.checkDName(dict.getDictName(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("字典名称已存在");
            return respBody;
        }
        dict.setCrAt(new Date());
        dict.setCrBy(currLoginUser().getId());
        dict.setUpAt(new Date());
        dict.setUpBy(currLoginUser().getId());
        dictService.insertAutoKey(dict);
        redisCacheService.refreshDictCache();
        respBody.setMsg("新增数据字典成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param dict
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody Dict dict) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(dict);
        List<Dict> list = dictService.checkDName(dict.getDictName(), dict.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("字典名称已存在");
            return respBody;
        }
        dict.setUpAt(new Date());
        dict.setUpBy(currLoginUser().getId());
        dictService.updateTplById(dict);
        redisCacheService.refreshDictCache();
        respBody.setMsg("更新数据字典成功");
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
            Dict dict = dictService.one(Long.parseLong(id));
            dict.setDeAt(new Date());
            dict.setDeBy(currLoginUser().getId());
            dictService.updateTplById(dict);
        }
        redisCacheService.refreshDictCache();
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
    public RespBody view(@PathVariable("id") Long id) {
        RespBody respBody = new RespBody();
        Dict dict = dictService.one(id);
        respBody.setBody(dict);
        return respBody;
    }

}
