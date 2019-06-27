package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.UserPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPostService extends BaseService<UserPost> {

    public List<UserPost> findBySysUserId(Long sysUserId){
        return sqlManager.lambdaQuery(UserPost.class).andIsNull(UserPost::getDeAt).andEq(UserPost::getSysUserId,sysUserId).select();
    }

}
