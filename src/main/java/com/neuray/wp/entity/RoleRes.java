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
 * 角色资源关系
 * gen by xtf 2019-01-29
 *
 */
@Data
@Builder
@Table(name = "ROLE_RES_T")
public class RoleRes extends BaseEntity {
    @Tolerate
    public RoleRes() {
    }

    private Long crBy;

    @NotBlank(message = "必填")
    private Long sysRoleId;

    private Date deAt;

    @AssignID
    private Long id;

    @NotBlank(message = "必填")
    private Long sysResId;

    private Date crAt;

    private Date upAt;

    private Long upBy;

    private Long deBy;

    @NotBlank(message = "必填")
    private Date effect;

    @NotBlank(message = "必填")
    private Date expired;

}