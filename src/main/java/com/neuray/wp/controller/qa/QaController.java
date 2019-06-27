////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.qa;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.qa.Qa;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.qa.QaService;
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
@RequestMapping("/qa")
@Slf4j
public class QaController extends BaseController {

    @Autowired
    private QaService qaService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Qa> query(@RequestBody Qa condition) {
            return qaService.many("qa.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Qa condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = qaService.page("qa.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param qa
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Qa qa) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(qa);
        qaService.insertAutoKey(qa);
        respBody.setMsg("新增问答成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param qa
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Qa qa) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(qa);
        qaService.update(qa);
        respBody.setMsg("更新问答成功");
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
            Qa qa=qaService.one(Long.parseLong(id));
            qaService.updateTplById(qa);
        }
        respBody.setMsg("删除问答成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Qa view(@PathVariable("id") Long id) {
        Qa qa=qaService.one("qa.sample",Qa.builder().id(id).build());
        return qa;
    }

}
