////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.artice;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.artice.InteractiveRecord;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.artice.InteractiveRecordService;
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
@RequestMapping("/interactiveRecord")
@Slf4j
public class InteractiveRecordController extends BaseController {

    @Autowired
    private InteractiveRecordService interactiveRecordService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<InteractiveRecord> query(@RequestBody InteractiveRecord condition) {
            return interactiveRecordService.many("interactiveRecord.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody InteractiveRecord condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = interactiveRecordService.page("interactiveRecord.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param interactiveRecord
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody InteractiveRecord interactiveRecord) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(interactiveRecord);
        interactiveRecordService.insertAutoKey(interactiveRecord);
        respBody.setMsg("新增互动成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param interactiveRecord
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody InteractiveRecord interactiveRecord) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(interactiveRecord);
        interactiveRecordService.update(interactiveRecord);
        respBody.setMsg("更新互动成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String,String> param) {
        RespBody respBody = new RespBody();
        if(StrUtil.isBlank(param.get("ids"))){throw new LogicException("删除操作失败，缺少删除数据");}
        String[] idArray=StrUtil.split(param.get("ids"),",");
        for(String id:idArray){
            InteractiveRecord interactiveRecord=interactiveRecordService.one(Long.parseLong(id));
            interactiveRecordService.updateTplById(interactiveRecord);
        }
        respBody.setMsg("删除互动成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public InteractiveRecord view(@PathVariable("id") Long id) {
        InteractiveRecord interactiveRecord=interactiveRecordService.one("interactiveRecord.sample",InteractiveRecord.builder().id(id).build());
        return interactiveRecord;
    }

}
