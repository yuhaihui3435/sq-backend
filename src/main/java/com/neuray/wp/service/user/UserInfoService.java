////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.user;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.entity.user.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import cn.hutool.core.util.StrUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoService extends BaseService<UserInfo> {

    public void checkNickName(String nickname,Long id){
        List<UserInfo> list;
        if(id==null){
            list=sqlManager.lambdaQuery(UserInfo.class).andEq(UserInfo::getNickname,nickname).select();
            if(!list.isEmpty()){
                throw new LogicException("昵称已经存在!");
            }
        }else{
            list=sqlManager.lambdaQuery(UserInfo.class).andEq(UserInfo::getNickname,nickname).andNotEq(UserInfo::getId,id).select();
            if(!list.isEmpty()){
                throw new LogicException("昵称已经存在!");
            }
        }
    }

}