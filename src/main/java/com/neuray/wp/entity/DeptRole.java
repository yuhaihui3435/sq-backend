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
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * 机构角色关系
 * gen by xtf 2019-01-21
 *
 */
@Data
@Builder
@Table(name = "DEPT_ROLE_T")
public class DeptRole extends BaseEntity {
    @Tolerate
    public DeptRole() {
    }
    @AutoID
    private Long id;

    private Long crBy;

    private Date expired;

    private Date deAt;

    private Long deBy;

    private Long upBy;

    private Long deptId;

    private Date effect;

    private Date upAt;

    private Long roleId;

    private Date crAt;

}