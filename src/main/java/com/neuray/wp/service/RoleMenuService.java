////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleMenuService extends BaseService<RoleMenu> {

    /**
     * @return : java.util.List<com.neuray.wp.entity.RoleMenu>
     */
    public List<RoleMenu> listByRoleIdAndMenuId(long roleId, long menuId) {
        return this.sqlManager.lambdaQuery(RoleMenu.class).andEq(RoleMenu::getSysRoleId, roleId).andEq(RoleMenu::getSysMenuId, menuId).andIsNull(RoleMenu::getDeAt).select();
    }

    /**
     * 根据角色查询所有菜单关联数据
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleMenu>
     */
    public List<RoleMenu> queryMenuByRole(String roleId) {
        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("sysMenuRight.queryMenuByRole", map);
    }

    /**
     * 根据角色id查询角色菜单关系
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleMenu>
     */
    public List<RoleMenu> queryRoleMenuByRole(long roleId) {
        return this.sqlManager.lambdaQuery(RoleMenu.class).andEq(RoleMenu::getSysRoleId, roleId).andIsNull(RoleMenu::getDeAt).select();
    }

}