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

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * 用户小组件关系
 * gen by xtf 2019-02-26
 *
 */
@Data
@Builder
@Table(name = "USER_WIDGET_T")
public class UserWidget extends BaseEntity {
    @Tolerate
    public UserWidget() {
    }

    @NotBlank(message = "必填")
    private Long widgetId;

    @NotBlank(message = "必填")
    private Date effect;

    @NotBlank(message = "必填")
    private Date expired;

    //组件默认高度
    @NotBlank(message = "组件默认高度必填")
    private Long defH;

    private Long deBy;

    private Date upAt;

    //0:角色挂进来的，1:用户直接挂进来的
    @NotBlank(message = "0:角色挂进来的，1:用户直接挂进来的必填")
    private Long type;

    private Long crBy;

    @AssignID
    private Long id;

    private Date crAt;

    //组件默认宽度
    @NotBlank(message = "组件默认宽度必填")
    private Long dftW;

    //x轴位置
    @NotBlank(message = "x轴位置必填")
    private Long pointX;

    private Long upBy;

    private Date deAt;

    @NotBlank(message = "必填")
    private Long sysUserId;

    //图标
    @NotBlank(message = "图标必填")
    private String icon;

    //刷新频率
    @NotBlank(message = "刷新频率必填")
    private Long refreshRate;

    //Y轴位置
    @NotBlank(message = "Y轴位置必填")
    private Long pointY;

}