package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.UserDept;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeptService extends BaseService<UserDept> {

    public List<UserDept> findByUserIdAndDeptIdAndDeAtIsNull(Long userId, Long deptId){
        return sqlManager.lambdaQuery(UserDept.class).andEq(UserDept::getSysUserId,userId).andEq(UserDept::getDeptId,deptId).andIsNull(UserDept::getDeAt).select();
    }
}
