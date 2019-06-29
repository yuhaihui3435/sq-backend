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

import java.util.*;
import javax.validation.constraints.*;

/*
 * 系统配置
 * gen by xtf 2019-01-09
 *
 */
@Data
@Builder
@Table(name = "SYS_CONF_T")
public class SysConf extends BaseEntity {
    @Tolerate
    public SysConf() {
    }

    @AutoID
    private Long id;
    //更新人
    private Long upBy;

    //配置名称
    @NotBlank(message = "配置名称必填")
    private String scName;

    //配置值
    @NotBlank(message = "配置值必填")
    private String scVal;

    //配置KEY
    @NotBlank(message = "配置KEY必填")
    private String scKey;

    //更新时间
    private Date upAt;

}