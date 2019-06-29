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

import java.util.Date;

/*
 * 用户机构变动关系
 * gen by xtf 2019-01-24
 *
 */
@Data
@Builder
@Table(name = "USER_DEPT_T")
public class UserDept extends BaseEntity {
    @Tolerate
    public UserDept() {
    }


    private Long sysUserId;

    private Date upAt;

    private Long deBy;

    private Date expired;

    private Date crAt;

    private Date deAt;

    private Long upBy;

    private Date effect;

    private Long deptId;

    private Long crBy;

    @AutoID
    private Long id;

}