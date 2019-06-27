////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.comment;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.comment.Comment;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.comment.CommentService;
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
@RequestMapping("/comment")
@Slf4j
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Comment> query(@RequestBody Comment condition) {
            return commentService.many("comment.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Comment condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = commentService.page("comment.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param comment
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Comment comment) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(comment);
        commentService.insertAutoKey(comment);
        respBody.setMsg("新增评论成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param comment
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Comment comment) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(comment);
        commentService.update(comment);
        respBody.setMsg("更新评论成功");
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
            Comment comment=commentService.one(Long.parseLong(id));
            commentService.updateTplById(comment);
        }
        respBody.setMsg("删除评论成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Comment view(@PathVariable("id") Long id) {
        Comment comment=commentService.one("comment.sample",Comment.builder().id(id).build());
        return comment;
    }

}
