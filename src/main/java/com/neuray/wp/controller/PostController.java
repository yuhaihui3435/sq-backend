////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Post;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/post")
@Slf4j
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/query")
    public List<Post> query(@RequestBody Post condition) {
        return postService.many("post.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody Post condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = postService.page("post.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param post
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody Post post) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(post);
        List<Post> list = postService.checkPostCode(post.getPostCode(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("职位编码已存在");
            return respBody;
        }
        list = postService.checkPostName(post.getPostName(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("职位名称已存在");
            return respBody;
        }
        post.setCrBy(currLoginUser().getId());
        post.setUpBy(currLoginUser().getId());
        postService.insertAutoKey(post);
        respBody.setMsg("新增职位成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param post
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody Post post) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(post);
        List<Post> list = postService.checkPostCode(post.getPostCode(), post.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("职位编码已存在");
            return respBody;
        }
        list = postService.checkPostName(post.getPostName(), post.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("职位名称已存在");
            return respBody;
        }
        post.setUpBy(currLoginUser().getId());
        postService.updateTplById(post);
        respBody.setMsg("更新职位成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.LOGICDEL, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) throw new LogicException("删除操作失败，缺少删除数据");
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            Post post = postService.one(Long.parseLong(id));
            post.setDeAt(new Date());
            post.setDeBy(currLoginUser().getId());
            postService.updateTplById(post);
        }
        respBody.setMsg("删除职位成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public Post view(@PathVariable("id") Long id) {
        Post post = postService.one("post.sample", Post.builder().id(id).build());
        return post;
    }

}
