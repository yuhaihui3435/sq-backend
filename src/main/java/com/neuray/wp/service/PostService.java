////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService extends BaseService<Post> {
    /**
     * 检查职位编码是否存在
     *
     * @param postCode
     * @param id
     * @return
     */
    public List<Post> checkPostCode(String postCode, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(Post.class).andEq(Post::getPostCode, postCode).andIsNull(Post::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(Post.class).andNotEq(Post::getId, id).andEq(Post::getPostCode, postCode).andIsNull(Post::getDeAt).select();
    }

    /**
     * 检查职位名称是否存在
     *
     * @param postName
     * @param id
     * @return
     */
    public List<Post> checkPostName(String postName, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(Post.class).andEq(Post::getPostName, postName).andIsNull(Post::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(Post.class).andNotEq(Post::getId, id).andEq(Post::getPostName, postName).andIsNull(Post::getDeAt).select();
    }


}