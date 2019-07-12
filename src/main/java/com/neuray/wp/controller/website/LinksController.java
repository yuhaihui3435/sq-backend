////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.website;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.website.Links;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.website.LinksService;
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
@RequestMapping("/links")
@Slf4j
public class LinksController extends BaseController {

    @Autowired
    private LinksService linksService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Links> query(@RequestBody Links condition) {
            return linksService.many("links.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Links condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = linksService.page("links.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param links
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Links links) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(links);
                            List<Links> list=linksService.checkName(links.getName(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("链接名称已存在");
                            return respBody;
                        }
        linksService.insertAutoKey(links);
        respBody.setMsg("新增友情链接成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param links
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Links links) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(links);
                                List<Links> list=linksService.checkName(links.getName(),links.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("链接名称已存在");
                                    return respBody;
                                }
        linksService.update(links);
        respBody.setMsg("更新友情链接成功");
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
            Links links=linksService.one(Long.parseLong(id));
            linksService.del(links);
        }
        respBody.setMsg("删除友情链接成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Links view(@PathVariable("id") Long id) {
        Links links=linksService.one("links.sample",Links.builder().id(id).build());
        return links;
    }

}
