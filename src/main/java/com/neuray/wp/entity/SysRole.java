////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
 * 系统角色
 * gen by xtf 2019-01-10
 *
 */
@Data
@Builder
@Table(name = "SYS_ROLE_T")
public class SysRole extends BaseEntity {
    @Tolerate
    public SysRole() {
    }

    //更新人
    private Long upBy;

    //主键
    @AssignID
    private Long id;

    //创建人
    private Long crBy;

    //状态-00:正常,01:停用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

    //角色编码
    @NotBlank(message = "角色编码必填")
    @Pattern(regexp = "^$|[A-Za-z0-9_\\-]+", message = "角色编码必须是字母或者数字")
    private String roleCode;

    //删除人
    private Long deBy;

    //删除时间
    private Date deAt;

    //角色名称
    @NotBlank(message = "角色名称必填")
    private String roleName;

    //创建时间
    private Date crAt;

    //更新时间
    private Date upAt;

}