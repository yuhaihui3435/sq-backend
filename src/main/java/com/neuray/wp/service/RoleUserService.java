package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleUserService extends BaseService<RoleUser> {

    /**
     * 根据角色查询角色用户关系
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleUser>
     */
    public List<RoleUser> queryRoleUserBuRole(long roleId) {
        return this.sqlManager.lambdaQuery(RoleUser.class).andEq(RoleUser::getSysRoleId, roleId).andIsNull(RoleUser::getDeAt).select();
    }

}
