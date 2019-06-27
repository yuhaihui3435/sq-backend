////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleRes;
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.SysResRight;
import com.neuray.wp.entity.SysRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleService extends BaseService<SysRole> {
    /**
     * 检查角色名称是否存在
     *
     * @param roleName
     * @param id
     * @return
     */
    public List<SysRole> checkRoleName(String roleName, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysRole.class).andEq(SysRole::getRoleName, roleName).andIsNull(SysRole::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(SysRole.class).andNotEq(SysRole::getId, id).andEq(SysRole::getRoleName, roleName).andIsNull(SysRole::getDeAt).select();
    }

    /**
     * 检查角色编码是否存在
     *
     * @param roleCode
     * @param id
     * @return
     */
    public List<SysRole> checkRoleCode(String roleCode, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysRole.class).andEq(SysRole::getRoleCode, roleCode).andIsNull(SysRole::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(SysRole.class).andNotEq(SysRole::getId, id).andEq(SysRole::getRoleCode, roleCode).andIsNull(SysRole::getDeAt).select();
    }

    /**
     * 根据用户id查询所有角色对象
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.SysRole>
     */
    public List<SysRole> queryRoleByUserId(Long userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("sysRole.queryRoleByUserId", map);
    }


}