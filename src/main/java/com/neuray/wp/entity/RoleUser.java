////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/*
 * 角色用户关系
 * gen by xtf 2019-01-30
 *
 */
@Data
@Builder
@Table(name = "ROLE_USER_T")
public class RoleUser extends BaseEntity {
    @Tolerate
    public RoleUser() {
    }

    private Long deBy;

    private Date expired;

    private Date upAt;

    @AssignID
    private Long id;

    private Long sysUserId;

    private Date crAt;

    private Date deAt;

    //00:正常,01:微调
    private String way;

    private Date effect;

    private Long crBy;

    private Long upBy;

    private Long sysRoleId;

}