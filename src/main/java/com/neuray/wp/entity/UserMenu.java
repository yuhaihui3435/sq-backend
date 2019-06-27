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
 * 用户菜单关系
 * gen by xtf 2019-02-28
 *
 */
@Data
@Builder
@Table(name = "USER_MENU_T")
public class UserMenu extends BaseEntity {
    @Tolerate
    public UserMenu() {
    }

    private Date upAt;

    @NotBlank(message = "必填")
    private Long sysUserId;

    private Date crAt;

    @AssignID
    private Long id;

    private Date deAt;

    private Long deBy;

    private Long upBy;

    @NotBlank(message = "必填")
    private Long sysMenuId;

    private Long crBy;

    private Date effect;

    private Date expired;

}