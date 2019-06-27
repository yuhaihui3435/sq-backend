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
 * 角色小组件关系表
 * gen by xtf 2019-02-01
 *
 */
@Data
@Builder
@Table(name = "ROLE_WIDGET_T")
public class RoleWidget extends BaseEntity {

    @AssignID
    private Long id;

    @Tolerate
    public RoleWidget() {
    }

    @NotBlank(message = "必填")
    private Date effect;

    private Date deAt;

    private Long upBy;

    @NotBlank(message = "必填")
    private Long roleId;

    private Long deBy;

    @NotBlank(message = "必填")
    private Date expired;

    private Date crAt;

    private Long crBy;

    private Date upAt;

    @NotBlank(message = "必填")
    private Long widgetId;

}