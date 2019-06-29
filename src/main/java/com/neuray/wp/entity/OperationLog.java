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
 * 操作日志
 * gen by xtf 2019-03-01
 *
 */
@Data
@Builder
@Table(name = "OPERATION_LOG_T")
public class OperationLog extends BaseEntity {
    @Tolerate
    public OperationLog() {
    }

    private Long deBy;

    //访问数据
    private String accessData;

    private Long upBy;

    private Date deAt;

    private Date crAt;

    @AutoID
    private Long id;

    private Date upAt;

    //访问类型
    @NotBlank(message = "访问类型必填")
    private String accessType;

    private Long crBy;

    //访问时间
    @NotBlank(message = "访问时间必填")
    private Date accessTime;

    //访问来源
    @NotBlank(message = "访问来源必填")
    private String accessSource;

    //访问功能
    @NotBlank(message = "访问功能必填")
    private String accessFunction;

    //用户id
    @NotBlank(message = "用户id必填")
    private Long sysUserId;

    //日志类型
    @NotBlank(message = "日志类型必填")
    private String accessLogtype;

    //访问路径
    @NotBlank(message = "访问路径必填")
    private String accessPath;

    //描述
    private String accessDesc;

    //返回结果
    private String accessResult;

    //异常
    private String accessException;

}