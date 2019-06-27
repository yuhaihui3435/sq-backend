////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleMenu;
import com.neuray.wp.entity.RoleRes;
import com.neuray.wp.entity.SysResRight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleResService extends BaseService<RoleRes> {

    public List<RoleRes> listByRoleIdAndResId(long roleId, long resId) {
        return this.sqlManager.lambdaQuery(RoleRes.class).andEq(RoleRes::getSysRoleId, roleId).andEq(RoleRes::getSysResId, resId).andIsNull(RoleRes::getDeAt).select();
    }

    /**
     * 根据角色查询资源关联对象
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleRes>
     */
    public List<RoleRes> queryResByRole(String roleId) {
        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("sysResRight.queryResByRole", map);
    }

    /**
     * 根据角色查询角色资源关系
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleRes>
     */
    public List<RoleRes> queryRoleResByRole(long roleId) {
        return this.sqlManager.lambdaQuery(RoleRes.class).andEq(RoleRes::getSysRoleId, roleId).andIsNull(RoleRes::getDeAt).select();
    }

}