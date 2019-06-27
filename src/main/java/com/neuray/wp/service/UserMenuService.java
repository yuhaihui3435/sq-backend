////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleMenu;
import com.neuray.wp.entity.UserMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserMenuService extends BaseService<UserMenu> {

    public List<UserMenu> listByUserIdAndMenuId(long userId, long menuId) {
        return this.sqlManager.lambdaQuery(UserMenu.class).andEq(UserMenu::getSysUserId, userId).andEq(UserMenu::getSysMenuId, menuId).andIsNull(UserMenu::getDeAt).select();
    }

    /**
     * 根据用户id查询菜单关系对象
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.UserMenu>
     */
    public List<UserMenu> queryMenuByUser(String userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("sysMenuRight.queryMenuByUser", map);
    }

}