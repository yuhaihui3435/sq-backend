////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;

import java.util.*;
import javax.validation.constraints.*;

/*
 * 角色菜单关系
 * gen by xtf 2019-01-21
 *
 */
@Data
@Builder
@Table(name = "ROLE_MENU_T")
public class RoleMenu extends BaseEntity {

    @AssignID
    private Long id;

    @Tolerate
    public RoleMenu() {
    }

    private Date crAt;

    private Date upAt;

    private Long upBy;
    @AssignID
    @NotBlank(message = "必填")
    private Long sysMenuId;

    //00正常，01停用
    @NotBlank(message = "00正常，01停用必填")
    private String status;

    private Long crBy;

    @NotBlank(message = "必填")
    private Date expired;

    private Long deBy;

    @NotBlank(message = "必填")
    private Date effect;
    @AssignID
    @NotBlank(message = "必填")
    private Long sysRoleId;

    private Date deAt;

}