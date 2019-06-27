////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.artice;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.artice.Artice;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.artice.ArticeService;
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
@RequestMapping("/artice")
@Slf4j
public class ArticeController extends BaseController {

    @Autowired
    private ArticeService articeService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Artice> query(@RequestBody Artice condition) {
            return articeService.many("artice.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Artice condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = articeService.page("artice.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param artice
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Artice artice) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(artice);
                            List<Artice> list=articeService.checkTitle(artice.getTitle(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
                            list=articeService.checkTitleEn(artice.getTitleEn(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
        artice.setCrBy(currLoginUser().getId());
        artice.setUpBy(currLoginUser().getId());
        articeService.insertAutoKey(artice);
        respBody.setMsg("新增文章成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param artice
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Artice artice) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(artice);
                                List<Artice> list=articeService.checkTitle(artice.getTitle(),artice.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
                                list=articeService.checkTitleEn(artice.getTitleEn(),artice.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
        artice.setUpBy(currLoginUser().getId());
        articeService.update(artice);
        respBody.setMsg("更新文章成功");
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
            Artice artice=articeService.one(Long.parseLong(id));
            artice.setDeAt(new Date());
            artice.setDeBy(currLoginUser().getId());
            articeService.updateTplById(artice);
        }
        respBody.setMsg("删除文章成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Artice view(@PathVariable("id") Long id) {
        Artice artice=articeService.one("artice.sample",Artice.builder().id(id).build());
        return artice;
    }

}
