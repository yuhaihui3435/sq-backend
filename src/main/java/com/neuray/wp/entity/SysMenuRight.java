////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;

import java.util.*;
import javax.validation.constraints.*;

/*
 * 系统菜单
 * gen by xtf 2019-01-14
 *
 */
@Data
@Builder
@Table(name = "SYS_MENU_RIGHT_T")
public class SysMenuRight extends BaseEntity {
    @Tolerate
    public SysMenuRight() {
    }

    //状态-00:正常,01:停用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

    //菜单代码
    @NotBlank(message = "菜单代码必填")
    private String smCode;

    //菜单地址
    @NotBlank(message = "菜单地址必填")
    private String smUri;

    //创建人
    private Long crBy;

    //菜单图标
    @NotBlank(message = "菜单图标必填")
    private String smIcon;

    //删除时间
    private Date deAt;

    //是否显示-0:显示,1:不显示
    @NotBlank(message = "是否显示必填")
    private String display;

    public String getDisplayStr() {
        String ret = null;
        if ("0".equals(display)) {
            ret = "显示";
        } else {
            ret = "不显示";
        }
        return ret;
    }

    //上级菜单
//    @NotNull(message = "上级菜单必填")
    private Long parentId;

    @AutoID
    private Long id;

    //菜单顺序号
    @NotNull(message = "菜单顺序号必填")
    private Long qn;

    //删除人
    private Long deBy;

    //菜单名称
    @NotBlank(message = "菜单名称必填")
    private String smName;

    //更新人
    private Long upBy;

    //更新时间
    private Date upAt;

    //创建时间
    private Date crAt;

}