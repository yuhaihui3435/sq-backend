package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.DeptRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptRoleService extends BaseService<DeptRole> {

    public List<DeptRole> findByDeptIdAndRoleIdAndDeAtIsNull(Long deptId, Long roleId){
        return sqlManager.lambdaQuery(DeptRole.class).andIsNull(DeptRole::getDeAt).andEq(DeptRole::getDeptId,deptId).andEq(DeptRole::getRoleId,roleId).select();
    }

}
