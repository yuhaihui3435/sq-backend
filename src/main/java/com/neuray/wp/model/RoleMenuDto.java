package com.neuray.wp.model;

import com.neuray.wp.entity.RoleMenu;
import com.neuray.wp.entity.SysMenuRight;
import lombok.Data;

@Data
public class RoleMenuDto {

    private Long roleId;
    private RoleMenu[] roleMenus;

}
