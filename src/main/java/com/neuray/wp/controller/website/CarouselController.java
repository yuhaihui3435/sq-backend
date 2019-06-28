////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.website;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.website.Carousel;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.website.CarouselService;
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
@RequestMapping("/carousel")
@Slf4j
public class CarouselController extends BaseController {

    @Autowired
    private CarouselService carouselService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Carousel> query(@RequestBody Carousel condition) {
            return carouselService.many("carousel.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Carousel condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = carouselService.page("carousel.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param carousel
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Carousel carousel) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(carousel);
        carousel.setCrBy(currLoginUser().getId());
        carousel.setUpBy(currLoginUser().getId());
        carouselService.insertAutoKey(carousel);
        respBody.setMsg("新增轮播配置成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param carousel
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Carousel carousel) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(carousel);
        carousel.setUpBy(currLoginUser().getId());
        carouselService.update(carousel);
        respBody.setMsg("更新轮播配置成功");
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
            Carousel carousel=carouselService.one(Long.parseLong(id));
            carousel.setDeAt(new Date());
            carousel.setDeBy(currLoginUser().getId());
            carouselService.updateTplById(carousel);
        }
        respBody.setMsg("删除轮播配置成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Carousel view(@PathVariable("id") Long id) {
        Carousel carousel=carouselService.one("carousel.sample",Carousel.builder().id(id).build());
        return carousel;
    }

}
