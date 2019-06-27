////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.UserMenu;
import com.neuray.wp.entity.UserRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserResService extends BaseService<UserRes> {

    public List<UserRes> listByUserIdAndResId(long userId, long resId) {
        return this.sqlManager.lambdaQuery(UserRes.class).andEq(UserRes::getSysUserId, userId).andEq(UserRes::getSysResId, resId).andIsNull(UserRes::getDeAt).select();
    }

    /**
     * 根据用户id查询资源关系对象
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.UserRes>
     */
    public List<UserRes> queryResByUser(String userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("sysResRight.queryResByUser", map);
    }

}