////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import com.neuray.wp.entity.artice.Artice;
import com.neuray.wp.service.artice.ArticeService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 文章
 * @Param
 * @Author zzq
 * @Date 2019/7/21 15:37
 * @return
 **/
@RestController
@RequestMapping("/api/article")
public class ApiArticleController {

    @Autowired
    private ArticeService articeService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Artice condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
//        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = articeService.page("artice.artice.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 详细
     *
     * @return
     */
    @PostMapping("/view")
    public Artice view(@RequestBody Artice condition) {
        Artice artice = articeService.one("artice.artice.sample", condition);
        return artice;
    }

}
